package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.Error: ImageVector
    get() {
        if (_Error != null) {
            return _Error!!
        }
        _Error = ImageVector.Builder(
            name = "Outlined.Error",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(11.743f, 2f)
                curveTo(5.678f, 2f, 1.011f, 7.771f, 2.181f, 13.652f)
                curveTo(2.942f, 17.479f, 6.014f, 20.551f, 9.841f, 21.313f)
                curveTo(15.789f, 22.496f, 21.493f, 17.747f, 21.493f, 11.75f)
                curveTo(21.488f, 6.442f, 17.051f, 2.006f, 11.743f, 2f)
                close()
                moveTo(10.993f, 7.25f)
                curveTo(10.993f, 6.852f, 11.345f, 6.5f, 11.743f, 6.5f)
                curveTo(12.141f, 6.5f, 12.493f, 6.852f, 12.493f, 7.25f)
                verticalLineTo(12.5f)
                curveTo(12.493f, 12.898f, 12.141f, 13.25f, 11.743f, 13.25f)
                curveTo(11.345f, 13.25f, 10.993f, 12.898f, 10.993f, 12.5f)
                verticalLineTo(7.25f)
                close()
                moveTo(11.743f, 17f)
                curveTo(11.298f, 17f, 10.874f, 16.717f, 10.704f, 16.305f)
                curveTo(10.436f, 15.66f, 10.847f, 14.906f, 11.524f, 14.771f)
                curveTo(12.209f, 14.635f, 12.868f, 15.176f, 12.868f, 15.875f)
                curveTo(12.868f, 16.488f, 12.356f, 17f, 11.743f, 17f)
                close()
            }
        }.build()

        return _Error!!
    }

private var _Error: ImageVector? = null
