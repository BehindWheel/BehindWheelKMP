package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.Undo: ImageVector
    get() {
        if (_Undo != null) {
            return _Undo!!
        }
        _Undo = ImageVector.Builder(
            name = "Outlined.Undo",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(7.563f, 20.813f)
                curveTo(7.01f, 20.813f, 6.563f, 20.365f, 6.563f, 19.813f)
                verticalLineTo(19.438f)
                curveTo(6.563f, 18.885f, 7.01f, 18.438f, 7.563f, 18.438f)
                horizontalLineTo(14.994f)
                curveTo(17.331f, 18.438f, 19.625f, 16.754f, 19.625f, 14.281f)
                curveTo(19.625f, 11.809f, 17.331f, 10.125f, 14.994f, 10.125f)
                horizontalLineTo(7.512f)
                lineTo(9.893f, 12.505f)
                curveTo(10.283f, 12.896f, 10.283f, 13.529f, 9.893f, 13.92f)
                lineTo(9.645f, 14.168f)
                curveTo(9.254f, 14.558f, 8.621f, 14.558f, 8.23f, 14.168f)
                lineTo(3f, 8.938f)
                lineTo(8.23f, 3.707f)
                curveTo(8.621f, 3.317f, 9.254f, 3.317f, 9.645f, 3.707f)
                lineTo(9.893f, 3.955f)
                curveTo(10.283f, 4.346f, 10.283f, 4.979f, 9.893f, 5.37f)
                lineTo(7.512f, 7.75f)
                horizontalLineTo(14.994f)
                curveTo(18.657f, 7.75f, 22f, 10.483f, 22f, 14.281f)
                curveTo(22f, 18.08f, 18.657f, 20.813f, 14.994f, 20.813f)
                horizontalLineTo(7.563f)
                close()
            }
        }.build()

        return _Undo!!
    }

private var _Undo: ImageVector? = null
