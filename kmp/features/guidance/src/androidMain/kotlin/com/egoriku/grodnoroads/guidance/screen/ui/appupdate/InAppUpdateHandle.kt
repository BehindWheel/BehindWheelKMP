package com.egoriku.grodnoroads.guidance.screen.ui.appupdate

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.egoriku.grodnoroads.datastore.edit
import com.google.android.play.core.ktx.AppUpdateResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun InAppUpdateHandle(onDownloaded: (complete: () -> Unit) -> Unit) {
    val dataStore = koinInject<DataStore<Preferences>>()
    val updatePreferences = remember { InAppUpdatePreferences(dataStore) }

    val scope = rememberCoroutineScope()
    val appUpdateResult = rememberAppUpdateResult()
    val updateLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            when (result.resultCode) {
                Activity.RESULT_CANCELED -> {
                    scope.launch {
                        updatePreferences.incrementRequestCount()
                    }
                }
                Activity.RESULT_OK -> {
                    scope.launch {
                        updatePreferences.resetUpdateRequestCount()
                    }
                }
            }
        }
    )

    LaunchedEffect(appUpdateResult) {
        when (appUpdateResult) {
            is AppUpdateResult.Available -> {
                if (updatePreferences.isShowInAppUpdate()) {
                    appUpdateResult.startFlexibleUpdate(updateLauncher)
                } else {
                    updatePreferences.incrementRequestCount()
                }
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

private class InAppUpdatePreferences(private val preferences: DataStore<Preferences>) {

    companion object {
        private const val REPEAT_UPDATE_AFTER = 5

        private val updateRequestCountKey = intPreferencesKey("update_request_count")
    }

    private val updateRequestCount = preferences.data.map { it[updateRequestCountKey] ?: 0 }
    private suspend fun setUpdateRequestCount(value: Int) {
        preferences.edit { this[updateRequestCountKey] = value }
    }

    suspend fun incrementRequestCount() = setUpdateRequestCount(updateRequestCount.first() + 1)

    suspend fun resetUpdateRequestCount() = setUpdateRequestCount(0)
    suspend fun isShowInAppUpdate(): Boolean {
        val count = updateRequestCount.first()
        return count == 0 || (count % REPEAT_UPDATE_AFTER) == 0
    }
}
