package com.egoriku.grodnoroads.map.foundation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.extensions.logD
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionButton(
    modifier: Modifier = Modifier,
    onLocationEnabled: () -> Unit,
    onLocationDisabled: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    val gmsLocationPermissionsState = rememberGmsLocationPermissionsState(
        onAccepted = onLocationEnabled,
        onDenied = onLocationDisabled
    )

    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    ) { permissions ->
        if (permissions.values.none { !it }) {
            gmsLocationPermissionsState.launchLocationRequest()
        }
    }

    Button(
        modifier = modifier.size(80.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 3.dp
        ),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),
        onClick = {
            locationPermissionsState.launchMultiplePermissionRequest()
        },
        content = content
    )
}

@Composable
internal fun rememberGmsLocationPermissionsState(
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
            Activity.RESULT_OK -> onAccepted()
            else -> onDenied()
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
internal interface GmsLocationPermissionState {
    fun launchLocationRequest()
}

@Stable
internal class MutableGmsLocationPermissionState(
    private val context: Context,
    private val onAccepted: () -> Unit
) : GmsLocationPermissionState {

    private val highPrecisionLowIntervalRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .setMinUpdateDistanceMeters(0f)
            .setMinUpdateIntervalMillis(1000)
            .build()

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