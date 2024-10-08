package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableFloatState
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filterNotNull

@Composable
actual fun Switch(
    checked: Boolean,
    modifier: Modifier,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val density = LocalDensity.current
    val haptic = LocalHapticFeedback.current

    val interactionSource = remember { MutableInteractionSource() }
    val colors = IosSwitchDefaults.colors()

    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    val animatedAspectRatio by animateFloatAsState(
        targetValue = if (isPressed || isHovered) 1.25f else 1f,
        animationSpec = AspectRationAnimationSpec,
        label = "animatedAspectRatio"
    )
    val animatedBackground by animateColorAsState(
        targetValue = colors.trackColor(enabled, checked).value,
        animationSpec = ColorAnimationSpec,
        label = "animatedBackground"
    )
    val animatedAlignment by animateFloatAsState(
        targetValue = if (checked) 1f else -1f,
        animationSpec = AlignmentAnimationSpec,
        label = "animatedAlignment"
    )

    val positionalThreshold = remember {
        (IosSwitchDefaults.Width - ThumbPadding * 2) - IosSwitchDefaults.Height
    }

    val dragThreshold = density.run { positionalThreshold.toPx() }

    var dragDistance by rememberMutableFloatState { 0f }
    val updatedChecked by rememberUpdatedState(checked)

    LaunchedEffect(Unit) {
        snapshotFlow {
            updatedChecked
        }.drop(1).collect {
            haptic.performHapticFeedback(HapticFeedbackType(3001))
        }
    }

    LaunchedEffect(dragThreshold) {
        snapshotFlow {
            when {
                dragDistance < 0f -> false
                dragDistance > dragThreshold -> true
                else -> null
            }
        }.filterNotNull().collect(onCheckedChange)
    }

    Column(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                enabled = enabled,
                role = Role.Switch,
                interactionSource = interactionSource,
                indication = null
            )
            .wrapContentSize(Alignment.Center)
            .size(IosSwitchDefaults.Width, IosSwitchDefaults.Height)
            .clip(IosSwitchDefaults.Shape)
            .drawBehind {
                drawRect(animatedBackground)
            }
            .padding(ThumbPadding),
    ) {
        Box(
            Modifier
                .fillMaxHeight()
                .aspectRatio(animatedAspectRatio)
                .pointerInput(dragThreshold) {
                    detectHorizontalDragGestures(
                        onDragStart = {
                            dragDistance = if (updatedChecked) dragThreshold else 0f
                        },
                        onHorizontalDrag = { c, v ->
                            dragDistance += v
                        }
                    )
                }
                .align(BiasAlignment.Horizontal(animatedAlignment))
                .let {
                    if (enabled) {
                        it.shadow(
                            elevation = IosSwitchDefaults.EnabledThumbElevation,
                            shape = IosSwitchDefaults.Shape
                        )
                    } else it.clip(IosSwitchDefaults.Shape)
                }
                .background(colors.thumbColor(enabled).value)
        )
    }
}

/**
 * Represents the colors used by a [Switch] in different states
 *
 * See [SwitchDefaults.colors] for the default implementation that follows Material
 * specifications.
 */
@Immutable
data class CupertinoSwitchColors(
    private val thumbColor: Color,
    private val disabledThumbColor: Color,
    private val checkedTrackColor: Color,
    private val checkedIconColor: Color,
    private val uncheckedTrackColor: Color,
    private val uncheckedIconColor: Color,
    private val disabledCheckedTrackColor: Color,
    private val disabledCheckedIconColor: Color,
    private val disabledUncheckedTrackColor: Color,
    private val disabledUncheckedIconColor: Color
) {
    /**
     * Represents the color used for the switch's thumb, depending on [enabled] and [checked].
     *
     * @param enabled whether the Switch is enabled or not
     */
    @Composable
    internal fun thumbColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(
            if (enabled) {
                thumbColor
            } else {
                disabledThumbColor
            }
        )
    }

    /**
     * Represents the color used for the switch's track, depending on [enabled] and [checked].
     *
     * @param enabled whether the Switch is enabled or not
     * @param checked whether the Switch is checked or not
     */
    @Composable
    internal fun trackColor(enabled: Boolean, checked: Boolean): State<Color> {
        return rememberUpdatedState(
            if (enabled) {
                if (checked) checkedTrackColor else uncheckedTrackColor
            } else {
                if (checked) disabledCheckedTrackColor else disabledUncheckedTrackColor
            }
        )
    }
}

@Immutable
object IosSwitchDefaults {

    internal val EnabledThumbElevation = 4.dp

    val Width: Dp = 51.dp

    val Height: Dp = 31.dp

    internal val Shape: CornerBasedShape = CircleShape

    @Composable
    @ReadOnlyComposable
    fun colors(
        thumbColor: Color = MaterialTheme.colorScheme.onPrimary,
        disabledThumbColor: Color = thumbColor,
        checkedTrackColor: Color = MaterialTheme.colorScheme.primary,
        checkedIconColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        uncheckedTrackColor: Color = Color.Gray.copy(alpha = .33f),
        uncheckedIconColor: Color = checkedIconColor,
        disabledCheckedTrackColor: Color = checkedTrackColor.copy(alpha = .33f),
        disabledCheckedIconColor: Color = checkedIconColor,
        disabledUncheckedTrackColor: Color = uncheckedTrackColor,
        disabledUncheckedIconColor: Color = checkedIconColor,
    ): CupertinoSwitchColors = CupertinoSwitchColors(
        thumbColor = thumbColor,
        disabledThumbColor = disabledThumbColor,
        checkedTrackColor = checkedTrackColor,
        checkedIconColor = checkedIconColor,
        uncheckedTrackColor = uncheckedTrackColor,
        uncheckedIconColor = uncheckedIconColor,
        disabledCheckedTrackColor = disabledCheckedTrackColor,
        disabledCheckedIconColor = disabledCheckedIconColor,
        disabledUncheckedTrackColor = disabledUncheckedTrackColor,
        disabledUncheckedIconColor = disabledUncheckedIconColor
    )
}

private val ThumbPadding = 2.dp

private val AspectRationAnimationSpec = cupertinoTween<Float>(durationMillis = 300)
private val ColorAnimationSpec = cupertinoTween<Color>(durationMillis = 300)
private val AlignmentAnimationSpec = AspectRationAnimationSpec

@GrodnoRoadsDarkLightPreview
@Composable
private fun PreviewSwitch() = GrodnoRoadsM3ThemePreview {
    var checked by rememberMutableState { true }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Switch(
            checked = checked,
            enabled = true,
            onCheckedChange = { checked = !checked }
        )
        Switch(
            checked = !checked,
            enabled = true,
            onCheckedChange = { checked = !checked }
        )
    }
}