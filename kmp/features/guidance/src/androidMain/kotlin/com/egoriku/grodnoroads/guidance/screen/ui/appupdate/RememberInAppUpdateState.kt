package com.egoriku.grodnoroads.guidance.screen.ui.appupdate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.ktx.AppUpdateResult
import com.google.android.play.core.ktx.requestUpdateFlow
import kotlinx.coroutines.flow.catch

@Composable
fun rememberAppUpdateResult(): AppUpdateResult {
    val context = LocalContext.current
    val updateManager = remember { AppUpdateManagerFactory.create(context) }
    var appUpdateResult by rememberMutableState<AppUpdateResult> { AppUpdateResult.NotAvailable }

    LaunchedEffect(updateManager) {
        updateManager
            .requestUpdateFlow()
            .catch { emit(AppUpdateResult.NotAvailable) }
            .collect {
                appUpdateResult = it
            }
    }

    return appUpdateResult
}
