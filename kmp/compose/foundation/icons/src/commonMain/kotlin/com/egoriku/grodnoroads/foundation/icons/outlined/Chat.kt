package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.Chat: ImageVector
    get() {
        if (_Chat != null) {
            return _Chat!!
        }
        _Chat = ImageVector.Builder(
            name = "Outlined.Chat",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(21.75f, 9f)
                curveTo(21.75f, 8.183f, 21.067f, 7.5f, 20.25f, 7.5f)
                horizontalLineTo(17.25f)
                verticalLineTo(4.5f)
                curveTo(17.25f, 3.683f, 16.567f, 3f, 15.75f, 3f)
                horizontalLineTo(3.75f)
                curveTo(2.933f, 3f, 2.25f, 3.683f, 2.25f, 4.5f)
                verticalLineTo(16.5f)
                curveTo(2.252f, 17.115f, 2.988f, 17.468f, 3.469f, 17.083f)
                lineTo(6.75f, 14.438f)
                verticalLineTo(17.25f)
                curveTo(6.75f, 18.067f, 7.433f, 18.75f, 8.25f, 18.75f)
                horizontalLineTo(17.024f)
                lineTo(20.531f, 21.583f)
                curveTo(21.01f, 21.971f, 21.75f, 21.618f, 21.75f, 21f)
                verticalLineTo(9f)
                close()
                moveTo(17.292f, 17.25f)
                horizontalLineTo(8.25f)
                verticalLineTo(14.25f)
                horizontalLineTo(15.75f)
                curveTo(16.567f, 14.25f, 17.25f, 13.567f, 17.25f, 12.75f)
                verticalLineTo(9f)
                horizontalLineTo(20.25f)
                verticalLineTo(19.43f)
                curveTo(19.543f, 18.858f, 17.625f, 17.25f, 17.292f, 17.25f)
                close()
            }
        }.build()

        return _Chat!!
    }

private var _Chat: ImageVector? = null
