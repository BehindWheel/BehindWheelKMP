package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.Moon: ImageVector
    get() {
        if (_Moon != null) {
            return _Moon!!
        }
        _Moon = ImageVector.Builder(
            name = "Outlined.Moon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(21.224f, 15.929f)
                curveTo(20.601f, 14.878f, 18.613f, 15.617f, 17.614f, 15.569f)
                curveTo(15.284f, 15.469f, 13.174f, 14.399f, 11.704f, 12.749f)
                curveTo(9.595f, 10.396f, 9.01f, 6.988f, 10.264f, 4.089f)
                curveTo(10.704f, 3.079f, 10.394f, 2.549f, 10.174f, 2.329f)
                curveTo(9.944f, 2.099f, 9.404f, 1.779f, 8.344f, 2.219f)
                curveTo(-0.307f, 5.857f, 0.297f, 18.383f, 9.064f, 21.419f)
                curveTo(13.306f, 22.895f, 18.224f, 21.361f, 20.904f, 17.719f)
                curveTo(21.574f, 16.789f, 21.394f, 16.199f, 21.224f, 15.929f)
                close()
            }
        }.build()

        return _Moon!!
    }

private var _Moon: ImageVector? = null
