package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.ChevronRight: ImageVector
    get() {
        if (_ChevronRight != null) {
            return _ChevronRight!!
        }
        _ChevronRight = ImageVector.Builder(
            name = "Outlined.ChevronRight",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(7.311f, 3.654f)
                curveTo(7.697f, 3.288f, 8.303f, 3.288f, 8.689f, 3.654f)
                lineTo(18f, 12.5f)
                lineTo(8.689f, 21.346f)
                curveTo(8.303f, 21.712f, 7.697f, 21.712f, 7.311f, 21.346f)
                lineTo(6.988f, 21.039f)
                curveTo(6.573f, 20.645f, 6.573f, 19.983f, 6.988f, 19.589f)
                lineTo(14.45f, 12.5f)
                lineTo(6.988f, 5.411f)
                curveTo(6.573f, 5.017f, 6.573f, 4.356f, 6.988f, 3.961f)
                lineTo(7.311f, 3.654f)
                close()
            }
        }.build()

        return _ChevronRight!!
    }

private var _ChevronRight: ImageVector? = null
