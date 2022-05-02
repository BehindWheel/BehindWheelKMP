package com.egoriku.grodnoroads.util.gms

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.egoriku.grodnoroads.extension.logD
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest

@Composable
fun rememberGmsLocationPermissionsState(
    onAccepted: () -> Unit,
    onDenied: () -> Unit
): GmsLocationPermissionState {
    val context = LocalContext.current

    val locationPermissionState = remember {
        MutableGmsLocationPermissionState(context = context, onAccepted = onAccepted)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        when (activityResult.resultCode) {
            Activity.RESULT_OK -> {
                logD("GMS location Accepted")
                onAccepted()
            }
            else -> {
                logD("GMS location Denied")
                onDenied()
            }
        }
    }

    DisposableEffect(locationPermissionState, launcher) {
        locationPermissionState.launcher = launcher
        onDispose {
            locationPermissionState.launcher = null
        }
    }

    return locationPermissionState
}

@Stable
interface GmsLocationPermissionState {
    fun launchLocationRequest()
}

@Stable
internal class MutableGmsLocationPermissionState(
    private val context: Context,
    private val onAccepted: () -> Unit
) : GmsLocationPermissionState {

    private val highPrecisionLowIntervalRequest = LocationRequest.create().apply {
        interval = 1000
        fastestInterval = 1000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        smallestDisplacement = 10f
    }

    private val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(highPrecisionLowIntervalRequest)
        .build()

    var launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>? = null

    override fun launchLocationRequest() {
        LocationServices.getSettingsClient(context).checkLocationSettings(builder)
            .addOnSuccessListener { onAccepted() }
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        val intentSenderRequest = IntentSenderRequest
                            .Builder(exception.resolution)
                            .build()

                        launcher?.launch(intentSenderRequest)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        logD("LocationServices: ${sendEx.message}")
                    }
                }
            }
    }
}