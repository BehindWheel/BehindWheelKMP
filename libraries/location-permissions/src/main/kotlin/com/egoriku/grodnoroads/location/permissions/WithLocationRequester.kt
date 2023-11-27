package com.egoriku.grodnoroads.location.permissions

import android.Manifest
import android.app.Activity
import androidx.activity.result.IntentSenderRequest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import com.egoriku.grodnoroads.foundation.core.LocalActivity
import com.egoriku.grodnoroads.location.permissions.LocationRequestStatus.GmsSettings
import com.egoriku.grodnoroads.location.permissions.LocationRequestStatus.Permissions
import com.egoriku.grodnoroads.location.permissions.internal.*
import kotlinx.coroutines.launch

@Composable
fun rememberLocationRequesterState() = remember { LocationRequesterState() }

@Composable
fun WithLocationRequester(
    locationRequesterState: LocationRequesterState,
    modifier: Modifier = Modifier,
    onStateChanged: (LocationRequestStatus) -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    // When in preview, early return a Box with the received modifier preserving layout
    if (LocalInspectionMode.current) {
        Box(modifier = modifier, content = content)
        return
    }

    val activity = LocalActivity.current

    val coroutineScope = rememberCoroutineScope()

    val resolutionResolver = rememberResolutionResolver {
        when (it.resultCode) {
            Activity.RESULT_OK -> onStateChanged(GmsSettings.GmsLocationEnabled)
            else -> onStateChanged(GmsSettings.GmsLocationDisabled)
        }
    }
    val settingsClient = rememberSettingsClient()
    val locationRequest = rememberLocationSettingsRequest()

    val permissionRequester = rememberPermissionRequester { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                coroutineScope.launch {
                    when (val state = settingsClient.invalidateLocationSettings(locationRequest)) {
                        is SettingsState.Resolvable -> {
                            val intent = state.exception.resolution
                            val request = IntentSenderRequest.Builder(intent).build()
                            resolutionResolver.launch(request)
                        }
                        SettingsState.Resolved -> onStateChanged(GmsSettings.GmsLocationEnabled)
                        SettingsState.Unresolvable -> onStateChanged(GmsSettings.GmsLocationDisabled)
                    }
                }
            }
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                onStateChanged(Permissions.DeniedFineLocation)
            }
            else -> {
                if (activity.shouldShowRationale(LOCATION_PERMISSIONS)) {
                    onStateChanged(Permissions.ShowRationale)
                } else {
                    onStateChanged(Permissions.Denied)
                }
            }
        }
    }

    DisposableEffect(locationRequesterState) {
        locationRequesterState.permissionRequester = permissionRequester
        onDispose {
            locationRequesterState.permissionRequester = null
        }
    }

    Box(modifier = modifier, content = content)
}

