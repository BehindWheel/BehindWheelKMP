package com.egoriku.grodnoroads.foundation.core.alignment

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.roundToInt

class OffsetAlignment(
    private val xOffset: Float,
    private val yOffset: Float
) : Alignment {
    override fun align(
        size: IntSize,
        space: IntSize,
        layoutDirection: LayoutDirection
    ): IntOffset {
        val x = when (layoutDirection) {
            LayoutDirection.Rtl -> 0f
            else -> (space.width - size.width).toFloat()
        }
        val y = (space.height - size.height).toFloat()

        val x1 = x * xOffset
        val y1 = y * yOffset

        return IntOffset(
            x = x1.roundToInt(),
            y = y1.roundToInt()
        )
    }
}
