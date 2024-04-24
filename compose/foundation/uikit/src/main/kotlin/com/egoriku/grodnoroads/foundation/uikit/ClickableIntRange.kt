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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
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
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import kotlinx.coroutines.launch

@Composable
fun ClickableIntRange(
    modifier: Modifier = Modifier,
    min: Int,
    max: Int,
    step: Int,
    value: Int,
    onLongClick: () -> Unit,
    onValueChange: (Int) -> Unit,
    formatter: (Int) -> String = { it.toString() }
) {
    var isError by rememberMutableState { false }

    Row(modifier) {
        Icon(
            modifier = Modifier
                .unboundClickable {
                    val decrement = value - step
                    when {
                        decrement >= min -> onValueChange(decrement)
                        else -> isError = true
                    }
                },
            imageVector = Icons.Default.RemoveCircle,
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
                text = formatter(targetCount),
                color = color,
                style = MaterialTheme.typography.titleSmall
            )
        }

        Icon(
            modifier = Modifier
                .unboundClickable {
                    val increment = value + step
                    when {
                        increment <= max -> onValueChange(increment)
                        else -> isError = true
                    }
                },
            imageVector = Icons.Default.AddCircle,
            contentDescription = null,
        )
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun ClickableIntRangePreview() = GrodnoRoadsM3ThemePreview {
    Box(modifier = Modifier.size(200.dp, 50.dp)) {
        var value by rememberMutableState { 500 }

        ClickableIntRange(
            modifier = Modifier.align(Center),
            min = 200,
            max = 1000,
            step = 100,
            value = value,
            onLongClick = {},
            onValueChange = { value = it },
            formatter = { "$it meters" }
        )
    }
}