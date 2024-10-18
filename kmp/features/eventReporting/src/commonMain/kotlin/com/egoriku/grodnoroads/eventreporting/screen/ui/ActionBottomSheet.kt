package com.egoriku.grodnoroads.eventreporting.screen.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.cancel
import com.egoriku.grodnoroads.compose.resources.send
import com.egoriku.grodnoroads.eventreporting.screen.ui.util.preUpPostDownNestedScrollConnection
import com.egoriku.grodnoroads.foundation.core.BackHandler
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryButton
import com.egoriku.grodnoroads.foundation.uikit.button.SecondaryButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

internal enum class DragAnchors {
    Start,
    End
}

private val sheetAnimationSpec = tween<Float>(durationMillis = 350)

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ActionBottomSheet(
    onDismiss: () -> Unit,
    onResult: () -> Unit,
    sendEnabled: Boolean,
    content: @Composable () -> Unit
) {
    val updatedOnDismiss by rememberUpdatedState(onDismiss)
    val updatedOnResult by rememberUpdatedState(onResult)

    val density = LocalDensity.current
    val focusManager = LocalFocusManager.current

    val scope = rememberCoroutineScope()

    val decayAnimationSpec = rememberSplineBasedDecay<Float>()

    val anchoredDraggableState = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.End,
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 50.dp.toPx() } },
            snapAnimationSpec = sheetAnimationSpec,
            decayAnimationSpec = decayAnimationSpec
        )
    }
    val internalOnDismissRequest: () -> Unit = remember {
        {
            scope.launch {
                anchoredDraggableState.animateTo(DragAnchors.End)
            }.invokeOnCompletion {
                updatedOnDismiss()
            }
        }
    }

    BackHandler(onBack = internalOnDismissRequest)

    var height by rememberMutableState { 0 }

    val diff by remember {
        derivedStateOf { height - anchoredDraggableState.offset }
    }
    val isExpanded by remember {
        derivedStateOf { diff > 80 || diff.isNaN() }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = true) {
            Scrim(
                color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f),
                onDismissRequest = internalOnDismissRequest
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset {
                            IntOffset(
                                x = 0,
                                y = anchoredDraggableState
                                    .requireOffset()
                                    .toInt()
                            )
                        }
                        .nestedScroll(
                            remember(anchoredDraggableState) {
                                anchoredDraggableState.preUpPostDownNestedScrollConnection()
                            }
                        )
                        .onSizeChanged {
                            height = it.height

                            val anchors = DraggableAnchors {
                                DragAnchors.Start at 0f
                                DragAnchors.End at it.height.toFloat()
                            }
                            anchoredDraggableState.updateAnchors(anchors)
                        }
                        .anchoredDraggable(
                            state = anchoredDraggableState,
                            orientation = Orientation.Vertical
                        )
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                focusManager.clearFocus()
                            })
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier
                            .widthIn(max = 600.dp)
                            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    ) {
                        val alpha by animateFloatAsState(
                            targetValue = if (isExpanded) 1f else 0f,
                            label = "alpha"
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            DragHandle()
                            Box(
                                modifier = Modifier.graphicsLayer {
                                    this.alpha = alpha
                                }
                            ) {
                                content()
                            }
                        }
                    }
                }

                LaunchedEffect(anchoredDraggableState.anchors.hasAnchorFor(DragAnchors.Start)) {
                    anchoredDraggableState.animateTo(DragAnchors.Start)
                }

                LaunchedEffect(anchoredDraggableState) {
                    snapshotFlow { anchoredDraggableState.currentValue }
                        .drop(1)
                        .filter { it == DragAnchors.End }
                        .collectLatest {
                            updatedOnDismiss()
                        }
                }
            }

            if (!diff.isNaN()) {
                var measuredHeight by rememberMutableState { 0 }

                val offsetY by animateIntAsState(
                    targetValue = if (isExpanded) -measuredHeight else 0,
                    label = "offset"
                )

                BottomActions(
                    modifier = Modifier
                        .widthIn(max = 600.dp)
                        .onSizeChanged {
                            if (measuredHeight < it.height) {
                                measuredHeight = it.height
                            }
                        }
                        .offset {
                            IntOffset(x = 0, y = measuredHeight)
                        }
                        .offset {
                            IntOffset(x = 0, y = offsetY)
                        },
                    sendEnabled = sendEnabled,
                    onCancel = internalOnDismissRequest,
                    onResult = {
                        updatedOnResult()
                        internalOnDismissRequest()
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomActions(
    sendEnabled: Boolean,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    onResult: () -> Unit
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            SecondaryButton(
                modifier = Modifier.weight(1f),
                onClick = onCancel
            ) {
                Text(text = stringResource(Res.string.cancel))
            }

            PrimaryButton(
                modifier = Modifier.weight(1f),
                enabled = sendEnabled,
                onClick = onResult
            ) {
                Text(text = stringResource(Res.string.send))
            }
        }
    }
}

@Composable
private fun DragHandle() {
    Surface(
        modifier = Modifier.padding(vertical = 16.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Spacer(modifier = Modifier.size(width = 32.dp, height = 4.dp))
    }
}

@Composable
private fun Scrim(
    color: Color,
    onDismissRequest: () -> Unit
) {
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(),
        label = "alpha"
    )
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(onDismissRequest) {
                detectTapGestures {
                    onDismissRequest()
                }
            }
            .clearAndSetSemantics {}
    ) {
        drawRect(color = color, alpha = alpha)
    }
}
