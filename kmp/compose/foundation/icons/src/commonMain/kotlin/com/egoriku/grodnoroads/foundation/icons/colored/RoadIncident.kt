package com.egoriku.grodnoroads.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Colored.RoadIncident: ImageVector
    get() {
        if (_RoadIncident != null) {
            return _RoadIncident!!
        }
        _RoadIncident = ImageVector.Builder(
            name = "Colored.RoadIncident",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 32f,
            viewportHeight = 32f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFFFFFF)),
                stroke = SolidColor(Color(0xFFB3261E)),
                strokeLineWidth = 1f
            ) {
                moveTo(14.706f, 3.296f)
                curveTo(15.286f, 2.235f, 16.714f, 2.235f, 17.294f, 3.296f)
                lineTo(30.29f, 27.07f)
                curveTo(30.9f, 28.186f, 30.111f, 29.5f, 28.996f, 29.5f)
                lineTo(3.004f, 29.5f)
                curveTo(1.889f, 29.5f, 1.1f, 28.186f, 1.71f, 27.07f)
                lineTo(14.706f, 3.296f)
                close()
            }
            path(fill = SolidColor(Color(0xFF232F34))) {
                moveTo(15.023f, 20.983f)
                curveTo(14.831f, 20.518f, 13.722f, 13.408f, 14.066f, 12.205f)
                curveTo(14.401f, 11.031f, 15.004f, 11.019f, 15.809f, 11.002f)
                curveTo(15.829f, 11.002f, 15.85f, 11.002f, 15.871f, 11.001f)
                curveTo(16.733f, 10.983f, 17.515f, 11.165f, 17.703f, 12.232f)
                curveTo(17.891f, 13.299f, 16.937f, 20.518f, 16.773f, 20.983f)
                curveTo(16.609f, 21.448f, 16.363f, 21.585f, 15.953f, 21.585f)
                curveTo(15.433f, 21.585f, 15.215f, 21.448f, 15.023f, 20.983f)
                close()
                moveTo(15.925f, 25.852f)
                curveTo(16.922f, 25.852f, 17.73f, 25.044f, 17.73f, 24.047f)
                curveTo(17.73f, 23.05f, 16.922f, 22.242f, 15.925f, 22.242f)
                curveTo(14.928f, 22.242f, 14.12f, 23.05f, 14.12f, 24.047f)
                curveTo(14.12f, 25.044f, 14.928f, 25.852f, 15.925f, 25.852f)
                close()
            }
        }.build()

        return _RoadIncident!!
    }

private var _RoadIncident: ImageVector? = null
