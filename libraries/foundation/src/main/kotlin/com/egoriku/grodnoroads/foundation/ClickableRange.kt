package com.egoriku.grodnoroads.foundation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.modifier.unboundClickable
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ClickableRange(
    modifier: Modifier = Modifier,
    min: Float,
    max: Float,
    step: Float,
    value: Float,
    onValueChange: (Float) -> Unit
) {
    var isError by rememberMutableState { false }

    Row(modifier) {
        Icon(
            modifier = Modifier
                .unboundClickable {
                    val decrement = value - step
                    if (decrement >= min) {
                        onValueChange(decrement)
                    } else {
                        isError = true
                    }
                },
            imageVector = Icons.Default.RemoveCircle,
            tint = MaterialTheme.colors.secondary,
            contentDescription = null,
        )
        AnimatedContent(
            modifier = Modifier.align(CenterVertically),
            targetState = value,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically { height -> height } + fadeIn() with
                            slideOutVertically { height -> -height } + fadeOut()
                } else {
                    slideInVertically { height -> -height } + fadeIn() with
                            slideOutVertically { height -> height } + fadeOut()
                }.using(
                    SizeTransform(clip = false)
                )
            }
        ) { targetCount ->
            val offsetX = remember { Animatable(0f) }
            val color by animateColorAsState(
                targetValue = if (isError) MaterialTheme.colors.error else LocalContentColor.current,
                animationSpec = tween(durationMillis = 250)
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
                    .offset(offsetX.value.dp, 0.dp),
                text = targetCount.toString(),
                color = color,
                style = MaterialTheme.typography.caption
            )
        }

        Icon(
            modifier = Modifier
                .unboundClickable {
                    val increment = value + step
                    if (increment <= max) {
                        onValueChange(increment)
                    } else {
                        isError = true
                    }
                },
            imageVector = Icons.Default.AddCircle,
            tint = MaterialTheme.colors.secondary,
            contentDescription = null,
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun RangeSettingPreview() {
    GrodnoRoadsTheme {
        Box(modifier = Modifier.size(200.dp, 50.dp)) {
            var value by rememberMutableState { 10f }

            ClickableRange(
                modifier = Modifier.align(Center),
                min = 10f,
                max = 15f,
                step = 1f,
                value = value,
                onValueChange = { value = it }
            )
        }
    }
}