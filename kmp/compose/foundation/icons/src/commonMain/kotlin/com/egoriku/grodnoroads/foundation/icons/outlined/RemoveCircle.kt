package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.RemoveCircle: ImageVector
    get() {
        if (_RemoveCircle != null) {
            return _RemoveCircle!!
        }
        _RemoveCircle = ImageVector.Builder(
            name = "Outlined.RemoveCircle",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(12f, 2f)
                curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
                curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
                curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
                curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
                close()
                moveTo(17f, 13f)
                lineTo(7f, 13f)
                lineTo(7f, 11f)
                lineTo(17f, 11f)
                lineTo(17f, 13f)
                close()
            }
        }.build()

        return _RemoveCircle!!
    }

private var _RemoveCircle: ImageVector? = null
