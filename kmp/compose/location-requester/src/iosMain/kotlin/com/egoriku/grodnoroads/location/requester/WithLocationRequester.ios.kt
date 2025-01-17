package com.egoriku.grodnoroads.location.requester

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.darwin.NSObject

@Composable
actual fun rememberLocationRequesterState() = remember { LocationRequesterState() }

@Composable
actual fun WithLocationRequester(
    locationRequesterState: LocationRequesterState,
    onStateChange: (LocationRequestStatus) -> Unit,
    modifier: Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val scope = rememberCoroutineScope()
    val locationDelegate = remember { LocationManagerDelegate() }
    val locationRequester = remember {
        LocationRequester(
            locationDelegate = locationDelegate,
            scope = scope
        )
    }

    LaunchedEffect(Unit) {
        locationRequester.events
            .onEach(onStateChange)
            .launchIn(this)
    }

    DisposableEffect(locationRequesterState) {
        locationRequesterState.locationRequester = locationRequester

        onDispose {
            locationRequesterState.locationRequester = null
        }
    }

    Box(modifier = modifier, content = content)
}

class LocationRequester(
    private val locationDelegate: LocationManagerDelegate,
    private val scope: CoroutineScope
) {
    private val mutex: Mutex = Mutex()

    private val _events = MutableSharedFlow<LocationRequestStatus>()
    val events = _events.asSharedFlow()

    fun request() {
        scope.launch {
            requestPermission()
        }
    }

    private suspend fun requestPermission() {
        val state = provideLocationPermission(locationDelegate.currentPermissionStatus())
        _events.emit(state)
    }

    private suspend fun provideLocationPermission(status: CLAuthorizationStatus): LocationRequestStatus {
        return when (status) {
            kCLAuthorizationStatusAuthorizedAlways,
            kCLAuthorizationStatusAuthorizedWhenInUse -> LocationRequestStatus.PermissionGranted

            kCLAuthorizationStatusNotDetermined -> {
                mutex.withLock {
                    val newStatus = suspendCoroutine { continuation ->
                        locationDelegate.requestLocationAccess { continuation.resume(it) }
                    }
                    provideLocationPermission(newStatus)
                }
            }

            kCLAuthorizationStatusDenied -> LocationRequestStatus.PermissionDenied
            else -> error("unknown location authorization status $status")
        }
    }
}

class LocationManagerDelegate :
    NSObject(),
    CLLocationManagerDelegateProtocol {

    private val locationManager = CLLocationManager()
    private var callback: ((CLAuthorizationStatus) -> Unit)? = null

    init {
        locationManager.delegate = this
    }

    override fun locationManagerDidChangeAuthorization(manager: CLLocationManager) {
        callback?.invoke(manager.authorizationStatus)
        callback = null
    }

    override fun locationManager(
        manager: CLLocationManager,
        didChangeAuthorizationStatus: CLAuthorizationStatus
    ) {
        callback?.invoke(didChangeAuthorizationStatus)
        callback = null
    }

    fun currentPermissionStatus(): CLAuthorizationStatus = locationManager.authorizationStatus

    fun requestLocationAccess(callback: (CLAuthorizationStatus) -> Unit) {
        this.callback = callback
        locationManager.requestAlwaysAuthorization()
    }
}
