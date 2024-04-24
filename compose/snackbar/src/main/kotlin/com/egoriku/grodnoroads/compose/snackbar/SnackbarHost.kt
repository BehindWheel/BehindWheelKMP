package com.egoriku.grodnoroads.compose.snackbar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AccessibilityManager
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarData
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarDuration
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarState
import kotlinx.coroutines.delay

@Composable
fun SnackbarHost(
    hostState: SnackbarState,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    snackbar: @Composable (SnackbarData) -> Unit = { Snackbar(it) }
) {
    val accessibilityManager = LocalAccessibilityManager.current

    val currentSnackbarData = hostState.currentSnackbarData
    LaunchedEffect(currentSnackbarData) {
        if (currentSnackbarData != null) {
            val duration = currentSnackbarData.message.duration.toMillis(
                hasAction = currentSnackbarData.message is SnackbarMessage.ActionMessage,
                accessibilityManager = accessibilityManager
            )
            delay(duration)
            currentSnackbarData.dismiss()
        }
    }

    AnimatedContent(
        modifier = modifier
            .fillMaxWidth()
            .padding(paddingValues),
        targetState = currentSnackbarData,
        transitionSpec = {
            if (initialState != targetState) {
                (slideInVertically { height -> height } + fadeIn()) togetherWith
                        slideOutVertically { height -> height } + fadeOut()
            } else {
                slideInVertically { height -> -height } + fadeIn() togetherWith
                        slideOutVertically { height -> -height } + fadeOut()
            }.using(
                SizeTransform(clip = false)
            )
        },
        label = "snackbar slide animation"
    ) { snackbarData ->
        when (snackbarData) {
            null -> Spacer(modifier = Modifier.fillMaxWidth())
            else -> snackbar(snackbarData)
        }
    }
}

internal fun SnackbarDuration.toMillis(
    hasAction: Boolean,
    accessibilityManager: AccessibilityManager?
): Long {
    val original = when (this) {
        SnackbarDuration.Indefinite -> Long.MAX_VALUE
        SnackbarDuration.Long -> 10000L
        SnackbarDuration.Short -> 4000L
    }
    if (accessibilityManager == null) {
        return original
    }
    return accessibilityManager.calculateRecommendedTimeoutMillis(
        originalTimeoutMillis = original,
        containsIcons = true,
        containsText = true,
        containsControls = hasAction
    )
}