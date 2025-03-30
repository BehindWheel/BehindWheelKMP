package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.Language: ImageVector
    get() {
        if (_Language != null) {
            return _Language!!
        }
        _Language = ImageVector.Builder(
            name = "Outlined.Language",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(4.5f, 9f)
                curveTo(4.5f, 4.335f, 8.939f, 0.745f, 13.463f, 1.644f)
                curveTo(16.406f, 2.23f, 18.77f, 4.594f, 19.355f, 7.537f)
                curveTo(20.266f, 12.113f, 16.612f, 16.5f, 12f, 16.5f)
                curveTo(7.916f, 16.496f, 4.504f, 13.083f, 4.5f, 9f)
                close()
                moveTo(18.23f, 15.495f)
                curveTo(14.746f, 18.85f, 9.045f, 18.796f, 5.624f, 15.376f)
                curveTo(2.204f, 11.955f, 2.15f, 6.254f, 5.505f, 2.77f)
                curveTo(5.795f, 2.476f, 5.789f, 1.987f, 5.491f, 1.701f)
                curveTo(5.193f, 1.415f, 4.704f, 1.429f, 4.423f, 1.731f)
                curveTo(1.633f, 4.639f, 0.753f, 9.01f, 2.2f, 12.771f)
                curveTo(3.647f, 16.531f, 7.23f, 19.185f, 11.25f, 19.473f)
                verticalLineTo(21f)
                horizontalLineTo(9f)
                curveTo(8.602f, 21f, 8.25f, 21.353f, 8.25f, 21.75f)
                curveTo(8.25f, 22.148f, 8.602f, 22.5f, 9f, 22.5f)
                horizontalLineTo(15f)
                curveTo(15.398f, 22.5f, 15.75f, 22.148f, 15.75f, 21.75f)
                curveTo(15.75f, 21.353f, 15.398f, 21f, 15f, 21f)
                horizontalLineTo(12.75f)
                verticalLineTo(19.474f)
                curveTo(15.196f, 19.305f, 17.504f, 18.279f, 19.269f, 16.577f)
                curveTo(19.571f, 16.296f, 19.585f, 15.807f, 19.299f, 15.509f)
                curveTo(19.013f, 15.211f, 18.524f, 15.205f, 18.23f, 15.495f)
                close()
            }
        }.build()

        return _Language!!
    }

@Suppress("ObjectPropertyName")
private var _Language: ImageVector? = null
