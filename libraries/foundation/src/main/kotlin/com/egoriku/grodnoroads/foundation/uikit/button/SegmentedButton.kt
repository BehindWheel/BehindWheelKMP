package com.egoriku.grodnoroads.foundation.uikit.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.uikit.button.OutlinedSegmentedButtonTokens.DisabledLabelTextOpacity
import com.egoriku.grodnoroads.foundation.uikit.button.OutlinedSegmentedButtonTokens.DisabledOutlineOpacity

/**
 * <a href="https://m3.material.io/components/segmented-buttons/overview" class="external" target="_blank">Material Segmented Button</a>.
 * Segmented buttons help people select options, switch views, or sort elements.
 *
 * A default Segmented Button. Also known as Outlined Segmented Button.
 *
 * This should typically be used inside of a [SegmentedButtonRow], see the corresponding
 * documentation for example usage.
 *
 * @param onClick called when this button is clicked
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param checked whether this button is checked or not
 * @param shape the shape for this button
 * @param border the border for this button, see [SegmentedButtonColors]
 * @param colors [SegmentedButtonColors] that will be used to resolve the colors used for this
 * Button in different states
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * @param content content to be rendered inside this button
 *
 * @sample androidx.compose.material3.samples.SegmentedButtonSingleSelectSample
 */
@Composable
@ExperimentalMaterial3Api
fun SegmentedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checked: Boolean = false,
    shape: Shape = ShapeNone,
    border: SegmentedButtonBorder = SegmentedButtonDefaults.border(),
    colors: SegmentedButtonColors = SegmentedButtonDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    var interactionCount: Int by remember { mutableIntStateOf(0) }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press,
                is FocusInteraction.Focus -> {
                    interactionCount++
                }

                is PressInteraction.Release,
                is FocusInteraction.Unfocus,
                is PressInteraction.Cancel -> {
                    interactionCount--
                }
            }
        }
    }

    // Selected state should take priority over interactions, add 5 instead of 1
    val zIndex by remember {
        derivedStateOf { interactionCount + if (checked) CheckedZIndexFactor else 0f }
    }

    OutlinedButton(
        modifier = modifier
            .layout { measurable, constraints ->
                val placeable = measurable.measure(constraints)
                layout(placeable.width, placeable.height) {
                    placeable.place(0, 0, zIndex)
                }
            }
            .semantics {
                selected = checked
            },
        interactionSource = interactionSource,
        shape = shape,
        onClick = onClick,
        border = border.borderStroke(enabled, checked, colors),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = colors.containerColor(enabled, checked),
            contentColor = colors.contentColor(enabled, checked),
        ),
        content = content
    )
}

@Composable
fun SegmentedButtonRow(
    modifier: Modifier = Modifier,
    inset: Dp = OutlinedSegmentedButtonTokens.OutlineWidth,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier
            .height(OutlinedSegmentedButtonTokens.ContainerHeight)
            .width(IntrinsicSize.Min)
            .selectableGroup(),
        content = content
    ) { measurables, constraints ->

        val width = measurables.fold(0) { curr, max ->
            maxOf(curr, max.maxIntrinsicWidth(Constraints.Infinity))
        }

        val placeables = measurables.map {
            it.measure(constraints.copy(minWidth = width))
        }

        layout(placeables.size * width, constraints.maxHeight) {
            val itemCount = placeables.size
            val startOffset = (itemCount - 1) * inset.roundToPx() / 2
            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(startOffset + index * (width - inset.roundToPx()), 0)
            }
        }
    }
}

@ExperimentalMaterial3Api
object SegmentedButtonDefaults {
    /**
     * Creates a [SegmentedButtonColors] that represents the different colors
     * used in a [SegmentedButtonRow] in different states.
     *
     * @param checkedContainerColor the color used for the container when enabled and checked
     * @param checkedContentColor the color used for the content when enabled and checked
     * @param checkedBorderColor the color used for the border when enabled and checked
     * @param uncheckedContainerColor the color used for the container when enabled and unchecked
     * @param uncheckedContentColor the color used for the content when enabled and unchecked
     * @param uncheckedBorderColor the color used for the border when enabled and checked
     * @param disabledCheckedContainerColor the color used for the container
     * when disabled and checked
     * @param disabledCheckedContentColor the color used for the content when disabled and checked
     * @param disabledCheckedBorderColor the color used for the border when disabled and checked
     * @param disabledUncheckedContainerColor the color used for the container
     * when disabled and unchecked
     * @param disabledUncheckedContentColor the color used for the content when disabled and
     * unchecked
     * @param disabledUncheckedBorderColor the color used for the border when disabled and unchecked
     */
    @Composable
    fun colors(
        checkedContainerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
        checkedContentColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
        checkedBorderColor: Color = MaterialTheme.colorScheme.outline,
        uncheckedContainerColor: Color = MaterialTheme.colorScheme.surface,
        uncheckedContentColor: Color = MaterialTheme.colorScheme.onSurface,
        uncheckedBorderColor: Color = checkedBorderColor,
        disabledCheckedContainerColor: Color = checkedContainerColor,
        disabledCheckedContentColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = DisabledLabelTextOpacity),
        disabledCheckedBorderColor: Color = MaterialTheme.colorScheme.outline
            .copy(alpha = DisabledOutlineOpacity),
        disabledUncheckedContainerColor: Color = uncheckedContainerColor,
        disabledUncheckedContentColor: Color = disabledCheckedContentColor,
        disabledUncheckedBorderColor: Color = checkedBorderColor,
    ): SegmentedButtonColors = SegmentedButtonColors(
        checkedContainerColor = checkedContainerColor,
        checkedContentColor = checkedContentColor,
        checkedBorderColor = checkedBorderColor,
        uncheckedContainerColor = uncheckedContainerColor,
        uncheckedContentColor = uncheckedContentColor,
        uncheckedBorderColor = uncheckedBorderColor,
        disabledCheckedContainerColor = disabledCheckedContainerColor,
        disabledCheckedContentColor = disabledCheckedContentColor,
        disabledCheckedBorderColor = disabledCheckedBorderColor,
        disabledUncheckedContainerColor = disabledUncheckedContainerColor,
        disabledUncheckedContentColor = disabledUncheckedContentColor,
        disabledUncheckedBorderColor = disabledUncheckedBorderColor,

        )

    /** The default [BorderStroke] factory used by [SegmentedButtonRow]. */
    fun border(): SegmentedButtonBorder = SegmentedButtonBorder()

    /** The default [Shape] for [SegmentedButton] insdie [SegmentedButtonRow]. */
    val shape: CornerBasedShape
        @Composable
        @ReadOnlyComposable
        get() = RoundedCornerShape(50)

    @Composable
    @ReadOnlyComposable
    fun shape(position: Int, count: Int, shape: CornerBasedShape = this.shape): Shape {
        return when (position) {
            0 -> RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp)
            count - 1 -> RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp)
            else -> ShapeNone
        }
    }

}

@ExperimentalMaterial3Api
@Immutable
class SegmentedButtonBorder {
    /** The default [BorderStroke] used by [SegmentedButtonRow]. */
    fun borderStroke(
        enabled: Boolean,
        checked: Boolean,
        colors: SegmentedButtonColors
    ): BorderStroke = BorderStroke(
        width = OutlinedSegmentedButtonTokens.OutlineWidth,
        color = colors.borderColor(enabled, checked)
    )
}

/**
 * The different colors used in parts of the [SegmentedButton] in different states
 *
 * @constructor create an instance with arbitrary colors, see [SegmentedButtonDefaults] for a
 * factory method using the default material3 spec
 *
 * @param checkedContainerColor the color used for the container when enabled and checked
 * @param checkedContentColor the color used for the content when enabled and checked
 * @param checkedBorderColor the color used for the border when enabled and checked
 * @param uncheckedContainerColor the color used for the container when enabled and unchecked
 * @param uncheckedContentColor the color used for the content when enabled and unchecked
 * @param uncheckedBorderColor the color used for the border when enabled and checked
 * @param disabledCheckedContainerColor the color used for the container when disabled and checked
 * @param disabledCheckedContentColor the color used for the content when disabled and checked
 * @param disabledCheckedBorderColor the color used for the border when disabled and checked
 * @param disabledUncheckedContainerColor the color used for the container
 * when disabled and unchecked
 * @param disabledUncheckedContentColor the color used for the content when disabled and unchecked
 * @param disabledUncheckedBorderColor the color used for the border when disabled and unchecked
 */
@Immutable
@ExperimentalMaterial3Api
class SegmentedButtonColors constructor(
    // enabled & checked
    val checkedContainerColor: Color,
    val checkedContentColor: Color,
    val checkedBorderColor: Color,
    // enabled & unchecked
    val uncheckedContainerColor: Color,
    val uncheckedContentColor: Color,
    val uncheckedBorderColor: Color,
    // disable & checked
    val disabledCheckedContainerColor: Color,
    val disabledCheckedContentColor: Color,
    val disabledCheckedBorderColor: Color,
    // disable & unchecked
    val disabledUncheckedContainerColor: Color,
    val disabledUncheckedContentColor: Color,
    val disabledUncheckedBorderColor: Color,

    ) {
    /**
     * Represents the color used for the SegmentedButton's border,
     * depending on [enabled] and [checked].
     *
     * @param enabled whether the [SegmentedButtonRow] is enabled or not
     * @param checked whether the [SegmentedButtonRow] item is checked or not
     */
    internal fun borderColor(enabled: Boolean, checked: Boolean): Color {
        return when {
            enabled && checked -> checkedBorderColor
            enabled && !checked -> uncheckedBorderColor
            !enabled && checked -> disabledCheckedContentColor
            else -> disabledUncheckedContentColor
        }
    }

    /**
     * Represents the content color passed to the items
     *
     * @param enabled whether the [SegmentedButtonRow] is enabled or not
     * @param checked whether the [SegmentedButtonRow] item is checked or not
     */
    internal fun contentColor(enabled: Boolean, checked: Boolean): Color {
        return when {
            enabled && checked -> checkedContentColor
            enabled && !checked -> uncheckedContentColor
            !enabled && checked -> disabledCheckedContentColor
            else -> disabledUncheckedContentColor
        }
    }

    /**
     * Represents the container color passed to the items
     *
     * @param enabled whether the [SegmentedButtonRow] is enabled or not
     * @param checked whether the [SegmentedButtonRow] item is checked or not
     */
    internal fun containerColor(enabled: Boolean, checked: Boolean): Color {
        return when {
            enabled && checked -> checkedContainerColor
            enabled && !checked -> uncheckedContainerColor
            !enabled && checked -> disabledCheckedContainerColor
            else -> disabledUncheckedContainerColor
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SegmentedButtonColors

        if (checkedBorderColor != other.checkedBorderColor) return false
        if (checkedContentColor != other.checkedContentColor) return false
        if (checkedContainerColor != other.checkedContainerColor) return false
        if (uncheckedBorderColor != other.uncheckedBorderColor) return false
        if (uncheckedContentColor != other.uncheckedContentColor) return false
        if (uncheckedContainerColor != other.uncheckedContainerColor) return false
        if (disabledCheckedBorderColor != other.disabledCheckedBorderColor) return false
        if (disabledCheckedContentColor != other.disabledCheckedContentColor) return false
        if (disabledCheckedContainerColor != other.disabledCheckedContainerColor) return false
        if (disabledUncheckedBorderColor != other.disabledUncheckedBorderColor) return false
        if (disabledUncheckedContentColor != other.disabledUncheckedContentColor) return false
        if (disabledUncheckedContainerColor != other.disabledUncheckedContainerColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = checkedBorderColor.hashCode()
        result = 31 * result + checkedContentColor.hashCode()
        result = 31 * result + checkedContainerColor.hashCode()
        result = 31 * result + uncheckedBorderColor.hashCode()
        result = 31 * result + uncheckedContentColor.hashCode()
        result = 31 * result + uncheckedContainerColor.hashCode()
        result = 31 * result + disabledCheckedBorderColor.hashCode()
        result = 31 * result + disabledCheckedContentColor.hashCode()
        result = 31 * result + disabledCheckedContainerColor.hashCode()
        result = 31 * result + disabledUncheckedBorderColor.hashCode()
        result = 31 * result + disabledUncheckedContentColor.hashCode()
        result = 31 * result + disabledUncheckedContainerColor.hashCode()
        return result
    }
}

private val ShapeNone = RoundedCornerShape(0.dp)
private const val CheckedZIndexFactor = 5f

internal object OutlinedSegmentedButtonTokens {
    val ContainerHeight = 40.0.dp
    val DisabledLabelTextOpacity = 0.38f
    val DisabledOutlineOpacity = 0.12f
    val OutlineWidth = 1.0.dp
}