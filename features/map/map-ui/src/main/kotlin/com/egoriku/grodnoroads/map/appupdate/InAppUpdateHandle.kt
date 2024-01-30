package com.egoriku.grodnoroads.map.appupdate

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.google.android.play.core.ktx.AppUpdateResult
import kotlinx.coroutines.launch

@Composable
fun InAppUpdateHandle(onDownloaded: (complete: () -> Unit) -> Unit) {
    val scope = rememberCoroutineScope()
    val appUpdateResult = rememberAppUpdateResult()
    val updateLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = {}
    )

    LaunchedEffect(appUpdateResult) {
        when (appUpdateResult) {
            is AppUpdateResult.Available -> {
                appUpdateResult.startFlexibleUpdate(updateLauncher)
            }
            is AppUpdateResult.Downloaded -> onDownloaded {
                scope.launch {
                    appUpdateResult.completeUpdate()
                }
            }
            else -> Unit
        }
    }
}