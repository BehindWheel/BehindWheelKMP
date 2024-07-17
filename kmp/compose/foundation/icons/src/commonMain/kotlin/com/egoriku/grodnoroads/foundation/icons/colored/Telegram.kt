package com.egoriku.grodnoroads.foundation.icons.colored

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Colored.Telegram: ImageVector
    get() {
        if (_Telegram != null) {
            return _Telegram!!
        }
        _Telegram = ImageVector.Builder(
            name = "Colored.Telegram",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 48f,
            viewportHeight = 48f
        ).apply {
            path(fill = SolidColor(Color(0xFF29B6F6))) {
                moveTo(24f, 4f)
                curveTo(13f, 4f, 4f, 13f, 4f, 24f)
                curveTo(4f, 35f, 13f, 44f, 24f, 44f)
                curveTo(35f, 44f, 44f, 35f, 44f, 24f)
                curveTo(44f, 13f, 35f, 4f, 24f, 4f)
                close()
            }
            path(fill = SolidColor(Color(0xFFFFFFFF))) {
                moveTo(34f, 15f)
                lineTo(30.3f, 34.1f)
                curveTo(30.3f, 34.1f, 30.1f, 35f, 29.1f, 35f)
                curveTo(28.5f, 35f, 28.2f, 34.7f, 28.2f, 34.7f)
                lineTo(20f, 28f)
                lineTo(16f, 26f)
                lineTo(10.9f, 24.6f)
                curveTo(10.9f, 24.6f, 10f, 24.3f, 10f, 23.6f)
                curveTo(10f, 23f, 10.9f, 22.7f, 10.9f, 22.7f)
                lineTo(32.2f, 14.2f)
                curveTo(32.2f, 14.2f, 32.9f, 14f, 33.3f, 14f)
                curveTo(33.6f, 14f, 33.9f, 14.1f, 33.9f, 14.5f)
                curveTo(34f, 14.8f, 34f, 15f, 34f, 15f)
                close()
            }
            path(fill = SolidColor(Color(0xFFB0BEC5))) {
                moveTo(23f, 30.5f)
                lineTo(19.6f, 33.9f)
                curveTo(19.6f, 33.9f, 19.5f, 34f, 19.3f, 34f)
                curveTo(19.2f, 34f, 19.2f, 34f, 19.1f, 34f)
                lineTo(20.1f, 28f)
                lineTo(23f, 30.5f)
                close()
            }
            path(fill = SolidColor(Color(0xFFCFD8DC))) {
                moveTo(29.9f, 18.2f)
                curveTo(29.7f, 18f, 29.4f, 17.9f, 29.2f, 18.1f)
                lineTo(16f, 26f)
                curveTo(16f, 26f, 18.1f, 31.9f, 18.4f, 32.9f)
                curveTo(18.7f, 33.9f, 19f, 33.9f, 19f, 33.9f)
                lineTo(20f, 27.9f)
                lineTo(29.8f, 18.8f)
                curveTo(30f, 18.7f, 30.1f, 18.4f, 29.9f, 18.2f)
                close()
            }
        }.build()

        return _Telegram!!
    }

private var _Telegram: ImageVector? = null
