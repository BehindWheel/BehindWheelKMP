package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.Close: ImageVector
    get() {
        if (_Close != null) {
            return _Close!!
        }
        _Close = ImageVector.Builder(
            name = "Outlined.Close",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(17.142f, 19.515f)
                curveTo(17.788f, 20.162f, 18.869f, 20.162f, 19.515f, 19.515f)
                curveTo(20.162f, 18.869f, 20.162f, 17.788f, 19.515f, 17.142f)
                lineTo(14.373f, 12f)
                lineTo(19.515f, 6.858f)
                curveTo(20.162f, 6.212f, 20.162f, 5.131f, 19.515f, 4.485f)
                curveTo(18.869f, 3.838f, 17.788f, 3.838f, 17.142f, 4.485f)
                lineTo(12f, 9.627f)
                lineTo(6.858f, 4.485f)
                curveTo(6.212f, 3.838f, 5.131f, 3.838f, 4.485f, 4.485f)
                curveTo(3.838f, 5.131f, 3.838f, 6.212f, 4.485f, 6.858f)
                lineTo(9.627f, 12f)
                lineTo(4.485f, 17.142f)
                curveTo(3.838f, 17.788f, 3.838f, 18.869f, 4.485f, 19.515f)
                curveTo(5.131f, 20.162f, 6.212f, 20.162f, 6.858f, 19.515f)
                lineTo(12f, 14.373f)
                lineTo(17.142f, 19.515f)
                close()
            }
        }.build()

        return _Close!!
    }

private var _Close: ImageVector? = null
