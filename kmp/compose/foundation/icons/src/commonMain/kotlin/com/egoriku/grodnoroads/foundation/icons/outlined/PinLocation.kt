package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.PinLocation: ImageVector
    get() {
        if (_PinLocation != null) {
            return _PinLocation!!
        }
        _PinLocation = ImageVector.Builder(
            name = "Outlined.PinLocation",
            defaultWidth = 12.dp,
            defaultHeight = 24.dp,
            viewportWidth = 16f,
            viewportHeight = 34f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(15.63f, 7.75f)
                curveTo(15.59f, 3.7f, 12.27f, 0f, 7.99f, 0f)
                curveTo(3.72f, 0f, 0.41f, 3.7f, 0.37f, 7.75f)
                curveTo(0.33f, 11.49f, 3.09f, 14.85f, 6.95f, 15.61f)
                lineTo(6.95f, 28.98f)
                curveTo(5.62f, 29.17f, 4.66f, 29.72f, 4.66f, 30.37f)
                curveTo(4.66f, 31.18f, 6.16f, 31.83f, 8f, 31.83f)
                curveTo(9.84f, 31.83f, 11.34f, 31.18f, 11.34f, 30.37f)
                curveTo(11.34f, 29.72f, 10.38f, 29.17f, 9.06f, 28.98f)
                lineTo(9.06f, 15.61f)
                curveTo(12.92f, 14.85f, 15.67f, 11.48f, 15.63f, 7.75f)
                close()
                moveTo(3.87f, 8.67f)
                curveTo(4.03f, 10.03f, 4.67f, 10.47f, 4.7f, 10.49f)
                curveTo(4.87f, 10.6f, 4.92f, 10.84f, 4.81f, 11.01f)
                curveTo(4.74f, 11.12f, 4.62f, 11.18f, 4.5f, 11.18f)
                curveTo(4.43f, 11.18f, 4.36f, 11.16f, 4.3f, 11.12f)
                curveTo(4.26f, 11.1f, 3.33f, 10.5f, 3.13f, 8.75f)
                curveTo(2.9f, 6.79f, 4.33f, 5.79f, 4.39f, 5.75f)
                curveTo(4.56f, 5.63f, 4.79f, 5.68f, 4.91f, 5.85f)
                curveTo(5.03f, 6.02f, 4.98f, 6.25f, 4.81f, 6.37f)
                curveTo(4.76f, 6.41f, 3.69f, 7.18f, 3.87f, 8.67f)
                close()
            }
        }.build()

        return _PinLocation!!
    }

private var _PinLocation: ImageVector? = null
