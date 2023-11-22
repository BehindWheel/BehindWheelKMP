package com.egoriku.grodnoroads.compose.snackbar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableFloatState
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.isLight
import com.egoriku.grodnoroads.foundation.theme.tonalElevation
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun Snackbar(snackbarData: SnackbarData) {
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
            val scaleFactor = abs(percent) / 0.7f
            1 - scaleFactor * scaleFactor
        }
    }

    Row(
        modifier = Modifier
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
                        if (targetValue != 0f) {
                            snackbarData.dismiss()
                        }
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
                        if (targetValue != 0f) {
                            snackbarData.dismiss()
                        }
                    }
                }
            )
    ) {
        if (snackbarData.visuals.withDismissAction) {
            ActionItem(
                title = snackbarData.visuals.title,
                description = snackbarData.visuals.description,
                onAction = snackbarData::performAction
            )
        } else {
            SimpleAction(title = snackbarData.visuals.title)
        }
    }
}

@Composable
fun ActionItem(
    title: String,
    description: String?,
    onAction: () -> Unit
) {
    Surface(
        color = when {
            MaterialTheme.colorScheme.isLight -> MaterialTheme.colorScheme.tertiaryContainer
            else -> MaterialTheme.colorScheme.surface
        },
        tonalElevation = MaterialTheme.tonalElevation,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                )
                IconButton(onClick = onAction) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        tint = MaterialTheme.colorScheme.tertiary,
                        contentDescription = null
                    )
                }
            }
            if (description != null) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = LocalContentColor.current.copy(alpha = 0.38f)
                )
            }
        }
    }
}

@Composable
fun SimpleAction(title: String) {
    Surface(
        color = when {
            MaterialTheme.colorScheme.isLight -> MaterialTheme.colorScheme.tertiaryContainer
            else -> MaterialTheme.colorScheme.surface
        },
        tonalElevation = MaterialTheme.tonalElevation,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                )
            }
        }
    }
}


@GrodnoRoadsDarkLightPreview
@Composable
private fun ActionItemPreview() = GrodnoRoadsM3ThemePreview {
    Box(Modifier.padding(32.dp)) {
        ActionItem(
            title = "Доступ к геолокации запрещен.",
            description = "Используются для доступа к данным карт",
            onAction = {}
        )
    }
}


@Preview
@Composable
private fun SnakbarPreview() = GrodnoRoadsM3ThemePreview {
    val hostState = remember { SnackbarState() }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarHost(
            hostState = hostState,
            modifier = Modifier.align(Alignment.BottomCenter),
            paddingValues = PaddingValues(16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                scope.launch {
                    hostState.showSnackbar(
                        message = "Службы определения геолокации выключены. Вы можете включить их в разделе настройки",
                        description = "Используются для доступа к данным карт и работы функций навигации",
                        withDismissAction = true,
                        actionLabel = "action"
                    )

                }
            }) {
                Text(text = "with action")
            }
            Button(onClick = {
                scope.launch {
                    hostState.showSnackbar(
                        message = "Службы определения геолокации выключены. Вы можете включить их в разделе настройки",
                        description = "Используются для доступа к данным карт и работы функций навигации",
                    )

                }
            }) {
                Text(text = "only message")
            }
        }
    }
}