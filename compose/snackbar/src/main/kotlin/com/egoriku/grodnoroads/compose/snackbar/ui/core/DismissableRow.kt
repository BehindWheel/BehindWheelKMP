package com.egoriku.grodnoroads.compose.snackbar.ui.core

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import com.egoriku.grodnoroads.foundation.core.rememberMutableFloatState
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
internal fun DismissableRow(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    var width by rememberMutableFloatState { 0f }
    var height by rememberMutableFloatState { 0f }
    val offsetPercentX by remember { derivedStateOf { offsetX.value / width } }
    val offsetPercentY by remember { derivedStateOf { offsetY.value / height } }
    val alpha by remember {
        derivedStateOf {
            val percent = when {
                offsetPercentX != 0f -> offsetPercentX
                offsetPercentY != 0f -> offsetPercentY
                else -> 0f
            }
            1 - 2 * abs(percent) / 0.7f
        }
    }
    Row(
        content = content,
        modifier = modifier
            .fillMaxWidth()
            .onSizeChanged {
                width = it.width.toFloat()
                height = it.height.toFloat()
            }
            .graphicsLayer {
                this.alpha = alpha
                translationX = offsetX.value
                translationY = offsetY.value
            }
            .draggable(
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        offsetX.snapTo(offsetX.value + delta)
                    }
                },
                orientation = Orientation.Horizontal,
                onDragStopped = {
                    val targetValue = when {
                        offsetPercentX >= 0.3f -> width
                        offsetPercentX <= -0.3f -> -width
                        else -> 0f
                    }
                    coroutineScope.launch {
                        offsetX.animateTo(
                            targetValue = targetValue,
                            animationSpec = tween(
                                durationMillis = 300,
                            )
                        )
                        if (targetValue != 0f) onDismiss()
                    }
                }
            )
            .draggable(
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        val targetValue = offsetY.value + delta
                        offsetY.snapTo(targetValue.coerceAtLeast(0f))
                    }
                },
                orientation = Orientation.Vertical,
                onDragStopped = {
                    val targetValue = when {
                        offsetPercentY >= 0.1f -> height
                        else -> 0f
                    }
                    coroutineScope.launch {
                        offsetY.animateTo(
                            targetValue = targetValue,
                            animationSpec = tween(
                                durationMillis = 300,
                            )
                        )
                        if (targetValue != 0f) onDismiss()
                    }
                }
            )
    )
}