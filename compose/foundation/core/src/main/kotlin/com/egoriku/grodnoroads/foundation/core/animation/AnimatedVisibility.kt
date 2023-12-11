package com.egoriku.grodnoroads.foundation.core.animation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FadeInOutAnimatedVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    label: String = "FadeInOutAnimatedVisibility",
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
        label = label,
        content = content
    )
}

@Composable
fun VerticalSlideAnimatedVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        content = content
    )
}

@Composable
fun HorizontalSlideAnimatedVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = slideInHorizontally { -it },
        exit = slideOutHorizontally { -it },
        content = content
    )
}
