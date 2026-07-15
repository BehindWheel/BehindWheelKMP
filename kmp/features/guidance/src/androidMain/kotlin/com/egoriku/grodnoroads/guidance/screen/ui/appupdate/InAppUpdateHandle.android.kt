package com.egoriku.grodnoroads.guidance.screen.ui.appupdate

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.egoriku.grodnoroads.datastore.edit
import com.egoriku.grodnoroads.shared.analytics.AnalyticsTracker
import com.google.android.play.core.install.InstallException
import com.google.android.play.core.ktx.AppUpdateResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
actual fun InAppUpdateHandle(onDownload: (complete: () -> Unit) -> Unit) {
    val updatedDownload by rememberUpdatedState(onDownload)

    val analyticsTracker = koinInject<AnalyticsTracker>()
    val dataStore = koinInject<DataStore<Preferences>>()
    val updatePreferences = remember { InAppUpdatePreferences(dataStore) }

    val scope = rememberCoroutineScope()
    val appUpdateResult = rememberAppUpdateResult()

    // Flag to prevent duplicate update flow launches
    var isUpdateInProgress by rememberSaveable { mutableStateOf(false) }

    val updateLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            // Reset flag when user completes or cancels the flow
            isUpdateInProgress = false

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
                val shouldShow = updatePreferences.isShowInAppUpdate()

                if (shouldShow && !isUpdateInProgress) {
                    try {
                        isUpdateInProgress = true
                        appUpdateResult.startFlexibleUpdate(updateLauncher)
                    } catch (e: InstallException) {
                        // InstallException is thrown synchronously when:
                        // - Another update is already in progress (errorCode = -8)
                        // - Other installation errors occur
                        // We catch it to prevent crash and log to analytics
                        isUpdateInProgress = false
                        analyticsTracker.trackInAppUpdateError(
                            errorCode = e.errorCode,
                            errorMessage = e.message ?: "InstallException"
                        )
                    } catch (e: Exception) {
                        // Catch any other unexpected exceptions
                        isUpdateInProgress = false
                        analyticsTracker.trackInAppUpdateError(
                            errorCode = -1,
                            errorMessage = e.message ?: "Unknown error"
                        )
                    }
                } else if (!shouldShow) {
                    updatePreferences.incrementRequestCount()
                }
            }
            is AppUpdateResult.Downloaded -> updatedDownload {
                scope.launch {
                    appUpdateResult.completeUpdate()
                }
            }
            else -> {}
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
