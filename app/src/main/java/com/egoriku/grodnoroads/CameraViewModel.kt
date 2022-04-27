package com.egoriku.grodnoroads

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.egoriku.grodnoroads.domain.model.AppMode
import com.egoriku.grodnoroads.domain.model.Camera
import com.egoriku.grodnoroads.domain.model.UserActionType
import com.egoriku.grodnoroads.domain.model.UserPosition
import com.egoriku.grodnoroads.domain.usecase.CameraUseCase
import com.egoriku.grodnoroads.extension.logD
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private val defaultPosition = UserPosition(
    latLng = LatLng(0.0, 0.0),
    bearing = 0f,
    speed = 0.0
)

class CameraViewModel(
    application: Application,
    private val useCase: CameraUseCase
) : AndroidViewModel(application) {

    val userActions = useCase.usersActions()

    private val _mode = MutableStateFlow(AppMode.Map)
    val mode = _mode.asStateFlow()

    private val _location = MutableStateFlow(defaultPosition)
    val location = _location.asStateFlow()

    private val _stationary = MutableStateFlow<List<Camera>>(emptyList())
    val stationary = _stationary.asStateFlow()

    init {
        viewModelScope.launch {
            _stationary.emit(useCase.loadStationary())
        }

        viewModelScope.launch {
            useCase.usersActions()
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            for (location: Location in locationResult.locations) {
                logD("locationCallback: ${location.latitude}, ${location.longitude}, ${location.bearing}")
                _location.tryEmit(
                    UserPosition(
                        latLng = LatLng(location.latitude, location.longitude),
                        bearing = location.bearing,
                        speed = if (location.hasSpeed()) location.speed * 18.0 / 5.0 else 0.0
                    )
                )
            }
        }
    }

    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            interval = 1000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        _mode.tryEmit(AppMode.Drive)
    }

    fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        _location.tryEmit(defaultPosition)

        _mode.tryEmit(AppMode.Map)
    }

    override fun onCleared() {
        super.onCleared()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    fun reportAction(latLng: LatLng, type: UserActionType) {
        viewModelScope.launch {
            useCase.reportAction(type = type, latLng = latLng)
        }
    }
}