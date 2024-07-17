package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.Notification: ImageVector
    get() {
        if (_Notification != null) {
            return _Notification!!
        }
        _Notification = ImageVector.Builder(
            name = "Outlined.Notification",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(19.34f, 14.49f)
                lineTo(18.34f, 12.83f)
                curveTo(18.13f, 12.46f, 17.94f, 11.76f, 17.94f, 11.35f)
                verticalLineTo(8.82f)
                curveTo(17.94f, 6.47f, 16.56f, 4.44f, 14.57f, 3.49f)
                curveTo(14.05f, 2.57f, 13.09f, 2f, 11.99f, 2f)
                curveTo(10.9f, 2f, 9.92f, 2.59f, 9.4f, 3.52f)
                curveTo(7.45f, 4.49f, 6.1f, 6.5f, 6.1f, 8.82f)
                verticalLineTo(11.35f)
                curveTo(6.1f, 11.76f, 5.91f, 12.46f, 5.7f, 12.82f)
                lineTo(4.69f, 14.49f)
                curveTo(4.29f, 15.16f, 4.2f, 15.9f, 4.45f, 16.58f)
                curveTo(4.69f, 17.25f, 5.26f, 17.77f, 6f, 18.02f)
                curveTo(7.94f, 18.68f, 9.98f, 19f, 12.02f, 19f)
                curveTo(14.06f, 19f, 16.1f, 18.68f, 18.04f, 18.03f)
                curveTo(18.74f, 17.8f, 19.28f, 17.27f, 19.54f, 16.58f)
                curveTo(19.8f, 15.89f, 19.73f, 15.13f, 19.34f, 14.49f)
                close()
            }
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(14.83f, 20.01f)
                curveTo(14.41f, 21.17f, 13.3f, 22f, 12f, 22f)
                curveTo(11.21f, 22f, 10.43f, 21.68f, 9.88f, 21.11f)
                curveTo(9.56f, 20.81f, 9.32f, 20.41f, 9.18f, 20f)
                curveTo(9.31f, 20.02f, 9.44f, 20.03f, 9.58f, 20.05f)
                curveTo(9.81f, 20.08f, 10.05f, 20.11f, 10.29f, 20.13f)
                curveTo(10.86f, 20.18f, 11.44f, 20.21f, 12.02f, 20.21f)
                curveTo(12.59f, 20.21f, 13.16f, 20.18f, 13.72f, 20.13f)
                curveTo(13.93f, 20.11f, 14.14f, 20.1f, 14.34f, 20.07f)
                curveTo(14.5f, 20.05f, 14.66f, 20.03f, 14.83f, 20.01f)
                close()
            }
        }.build()

        return _Notification!!
    }

private var _Notification: ImageVector? = null
