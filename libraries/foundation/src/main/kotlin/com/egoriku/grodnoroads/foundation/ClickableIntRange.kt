package com.egoriku.grodnoroads.foundation

import androidx.compose.animation.*
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.modifier.unboundClickable
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
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
                                x at durationMillis / 10 * i with easing
                            }
                        },
                    )
                }
            }

            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .offset(offsetX.value.dp, 0.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(onLongPress = { onLongClick() })
                    },
                text = formatter(targetCount),
                color = color,
                style = MaterialTheme.typography.bodySmall
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

@GrodnoRoadsPreview
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