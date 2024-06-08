package com.egoriku.grodnoroads.location.requester

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
actual fun rememberLocationRequesterState() = remember { LocationRequesterState() }

@Composable
actual fun WithLocationRequester(
    locationRequesterState: LocationRequesterState,
    modifier: Modifier,
    onStateChanged: (LocationRequestStatus) -> Unit,
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
            .onEach(onStateChanged)
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
    private val _events = MutableSharedFlow<LocationRequestStatus>()
    val events = _events.asSharedFlow()

    fun request() {
        scope.launch {
            requestPermission()
        }
    }

    private suspend fun requestPermission() {
        val state = provideLocationPermission(CLLocationManager.authorizationStatus())
        _events.emit(state)
    }

    private suspend fun provideLocationPermission(status: CLAuthorizationStatus): LocationRequestStatus {
        return when (status) {
            kCLAuthorizationStatusAuthorizedAlways,
            kCLAuthorizationStatusAuthorizedWhenInUse -> LocationRequestStatus.GmsLocationEnabled

            kCLAuthorizationStatusNotDetermined -> {
                val newStatus = suspendCoroutine { continuation ->
                    locationDelegate.requestLocationAccess { continuation.resume(it) }
                }
                provideLocationPermission(newStatus)
            }

            kCLAuthorizationStatusDenied -> LocationRequestStatus.PermissionDenied
            else -> error("unknown location authorization status $status")
        }
    }
}

class LocationManagerDelegate : NSObject(), CLLocationManagerDelegateProtocol {
    private var callback: ((CLAuthorizationStatus) -> Unit)? = null

    private val locationManager = CLLocationManager()

    init {
        locationManager.delegate = this
    }

    fun requestLocationAccess(callback: (CLAuthorizationStatus) -> Unit) {
        this.callback = callback

        locationManager.requestWhenInUseAuthorization()
    }

    override fun locationManager(
        manager: CLLocationManager,
        didChangeAuthorizationStatus: CLAuthorizationStatus
    ) {
        callback?.invoke(didChangeAuthorizationStatus)
        callback = null
    }
}