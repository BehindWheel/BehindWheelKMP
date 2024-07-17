package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.core.unboundClickable
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.AddCircle
import com.egoriku.grodnoroads.foundation.icons.outlined.RemoveCircle
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ClickableFloatRange(
    modifier: Modifier = Modifier,
    min: Float,
    max: Float,
    step: Float,
    value: Float,
    onLongClick: () -> Unit,
    onValueChange: (Float) -> Unit
) {
    var isError by rememberMutableState { false }

    Row(modifier) {
        Icon(
            modifier = Modifier
                .unboundClickable {
                    val decrement = decrement(value, step)
                    if (decrement >= min) {
                        onValueChange(decrement)
                    } else {
                        isError = true
                    }
                },
            imageVector = GrodnoRoads.Outlined.RemoveCircle,
            contentDescription = null,
        )
        AnimatedContent(
            modifier = Modifier.align(CenterVertically),
            targetState = value,
            transitionSpec = {
                if (targetState > initialState) {
                    (slideInVertically { height -> height } + fadeIn()) togetherWith
                            slideOutVertically { height -> -height } + fadeOut()
                } else {
                    slideInVertically { height -> -height } + fadeIn() togetherWith
                            slideOutVertically { height -> height } + fadeOut()
                }.using(
                    SizeTransform(clip = false)
                )
            },
            label = "slide + fade animation"
        ) { targetCount ->
            val offsetX = remember { Animatable(0f) }
            val color by animateColorAsState(
                targetValue = if (isError) MaterialTheme.colorScheme.error else LocalContentColor.current,
                animationSpec = tween(durationMillis = 250),
                label = "color"
            )

            LaunchedEffect(offsetX.isRunning) {
                if (!offsetX.isRunning) {
                    isError = false
                }
            }

            LaunchedEffect(isError) {
                if (!isError) return@LaunchedEffect
                launch {
                    offsetX.animateTo(
                        targetValue = 0f,
                        animationSpec = keyframes {
                            durationMillis = 600
                            val easing = FastOutLinearInEasing
                            for (i in 1..8) {
                                val x = when (i % 3) {
                                    0 -> 4f
                                    1 -> -4f
                                    else -> 0f
                                }
                                x at durationMillis / 10 * i using easing
                            }
                        },
                    )
                }
            }

            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .offset {
                        IntOffset(offsetX.value.toInt(), 0)
                    }
                    .pointerInput(Unit) {
                        detectTapGestures(onLongPress = { onLongClick() })
                    },
                text = targetCount.toString(),
                color = color,
                style = MaterialTheme.typography.titleSmall
            )
        }

        Icon(
            modifier = Modifier
                .unboundClickable {
                    val increment = increment(value, step)
                    if (increment <= max) {
                        onValueChange(increment)
                    } else {
                        isError = true
                    }
                },
            imageVector = GrodnoRoads.Outlined.AddCircle,
            contentDescription = null,
        )
    }
}

private fun increment(value: Float, step: Float): Float {
    val result = value + step
    return (result * 10.0f).roundToInt() / 10.0f
}

private fun decrement(value: Float, step: Float): Float {
    val result = value - step
    return (result * 10.0f).roundToInt() / 10.0f
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun ClickableFloatRangePreview() = GrodnoRoadsM3ThemePreview {
    Box(modifier = Modifier.size(200.dp, 50.dp)) {
        var value by rememberMutableState { 10f }

        ClickableFloatRange(
            modifier = Modifier.align(Center),
            min = 10f,
            max = 15f,
            step = 0.1f,
            value = value,
            onLongClick = {},
            onValueChange = { value = it }
        )
    }
}