package com.egoriku.grodnoroads.location.permissions.internal

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import kotlinx.coroutines.tasks.await

@Composable
internal fun rememberResolutionResolver(onResult: (ActivityResult) -> Unit) =
    rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = onResult
    )

@Composable
internal fun rememberSettingsClient(): SettingsClient {
    val context = LocalContext.current
    return remember(context) { LocationServices.getSettingsClient(context) }
}

@Composable
internal fun rememberLocationSettingsRequest() = remember {
    LocationSettingsRequest.Builder()
        .addLocationRequest(
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .setMinUpdateDistanceMeters(0f)
                .setMinUpdateIntervalMillis(1000)
                .build()
        )
        .build()
}

internal suspend fun SettingsClient.invalidateLocationSettings(
    request: LocationSettingsRequest
) = runCatching {
    checkLocationSettings(request).await()
    SettingsState.Resolved
}.getOrElse { throwable ->
    when (throwable) {
        is ApiException -> {
            runCatching {
                SettingsState.Resolvable(throwable as ResolvableApiException)
            }.getOrDefault(SettingsState.Unresolvable)
        }
        else -> SettingsState.Unresolvable
    }
}

internal sealed class SettingsState {
    data object Resolved : SettingsState()
    data object Unresolvable : SettingsState()
    data class Resolvable(@Stable val exception: ResolvableApiException) : SettingsState()
}