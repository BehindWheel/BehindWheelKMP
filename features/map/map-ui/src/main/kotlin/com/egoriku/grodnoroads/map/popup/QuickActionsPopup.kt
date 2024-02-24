package com.egoriku.grodnoroads.map.popup

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.animation.FadeInOutAnimatedVisibility
import com.egoriku.grodnoroads.foundation.core.noIndicationClick
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryInverseCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size.Small

@Composable
fun QuickActionsPopup(
    opened: Boolean,
    onExpand: () -> Unit,
    onClosed: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val transition = updateTransition(targetState = opened, label = "QuickActions")

    val percent by transition.animateDp(
        label = "animateClipShape",
        transitionSpec = {
            spring(stiffness = if (initialState) Spring.StiffnessVeryLow else Spring.StiffnessMedium)
        }
    ) { isOpen -> if (isOpen) 25.dp else 50.dp }

    BackHandler(enabled = opened, onBack = onClosed)

    Box(modifier = Modifier.fillMaxSize()) {
        FadeInOutAnimatedVisibility(
            visible = opened,
            modifier = Modifier.matchParentSize(),
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f))
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = { onClosed() })
                    }
            )
        }
        Box(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.TopEnd),
            contentAlignment = Alignment.TopEnd
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
                    .padding(1.dp)
                    .clip(shape = RoundedCornerShape(percent))
                    .noIndicationClick()
                    .background(MaterialTheme.colorScheme.surface)
                    .animateContentSize(animationSpec = spring(Spring.DampingRatioLowBouncy))
            ) {
                val width = this@BoxWithConstraints.maxWidth
                val height = this@BoxWithConstraints.maxHeight

                AnimatedVisibility(
                    visible = opened,
                    enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessVeryLow)),
                    exit = fadeOut(animationSpec = spring(stiffness = Spring.StiffnessHigh)),
                    modifier = Modifier.animateContentSize(
                        animationSpec = spring(Spring.DampingRatioLowBouncy)
                    )
                ) {
                    val widthModifier = when {
                        width >= height -> Modifier.width(420.dp)
                        else -> Modifier.fillMaxWidth()
                    }

                    AnimatedVisibility(
                        modifier = widthModifier,
                        visible = opened,
                        enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessVeryLow)),
                        exit = fadeOut(animationSpec = spring(stiffness = Spring.StiffnessHigh)),
                    ) {
                        Column(
                            modifier = Modifier.width(IntrinsicSize.Max),
                            content = content
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = !opened,
                enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessVeryLow)),
                exit = fadeOut(),
            ) {
                PrimaryInverseCircleButton(onClick = onExpand, size = Small) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview(device = "id:pixel_7a")
@Preview(device = "spec:parent=pixel_7a,orientation=landscape")
@Composable
private fun QuickActionsPopupPreview() = GrodnoRoadsM3ThemePreview {
    var opened by rememberMutableState { true }

    QuickActionsPopup(
        opened = opened,
        onExpand = { opened = true },
        onClosed = { opened = false },
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Action 1")
            Text("Action 2")
        }
    }
}