package com.egoriku.grodnoroads.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Colored.Info: ImageVector
    get() {
        if (_Info != null) {
            return _Info!!
        }
        _Info = ImageVector.Builder(
            name = "Colored.Info",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 48f,
            viewportHeight = 48f
        ).apply {
            path(fill = SolidColor(Color(0xFF2196F3))) {
                moveTo(24f, 24f)
                moveTo(6f, 24f)
                curveTo(6f, 19.228f, 7.898f, 14.646f, 11.272f, 11.272f)
                curveTo(14.646f, 7.898f, 19.228f, 6f, 24f, 6f)
                curveTo(28.772f, 6f, 33.354f, 7.898f, 36.728f, 11.272f)
                curveTo(40.102f, 14.646f, 42f, 19.228f, 42f, 24f)
                curveTo(42f, 28.772f, 40.102f, 33.354f, 36.728f, 36.728f)
                curveTo(33.354f, 40.102f, 28.772f, 42f, 24f, 42f)
                curveTo(19.228f, 42f, 14.646f, 40.102f, 11.272f, 36.728f)
                curveTo(7.898f, 33.354f, 6f, 28.772f, 6f, 24f)
            }
            path(fill = SolidColor(Color(0xFFFFFFFF))) {
                moveTo(24f, 15.5f)
                moveTo(21.5f, 15.5f)
                curveTo(21.5f, 14.837f, 21.764f, 14.201f, 22.232f, 13.732f)
                curveTo(22.701f, 13.264f, 23.337f, 13f, 24f, 13f)
                curveTo(24.663f, 13f, 25.299f, 13.264f, 25.768f, 13.732f)
                curveTo(26.236f, 14.201f, 26.5f, 14.837f, 26.5f, 15.5f)
                curveTo(26.5f, 16.163f, 26.236f, 16.799f, 25.768f, 17.268f)
                curveTo(25.299f, 17.736f, 24.663f, 18f, 24f, 18f)
                curveTo(23.337f, 18f, 22.701f, 17.736f, 22.232f, 17.268f)
                curveTo(21.764f, 16.799f, 21.5f, 16.163f, 21.5f, 15.5f)
            }
            path(fill = SolidColor(Color(0xFFFFFFFF))) {
                moveTo(22f, 21f)
                lineTo(26f, 21f)
                lineTo(26f, 35f)
                lineTo(22f, 35f)
                close()
            }
        }.build()

        return _Info!!
    }

private var _Info: ImageVector? = null
