package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.NotificationBadge: ImageVector
    get() {
        if (_NotificationBadge != null) {
            return _NotificationBadge!!
        }
        _NotificationBadge = ImageVector.Builder(
            name = "Outlined.NotificationBadge",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(17.777f, 18f)
                horizontalLineTo(6.223f)
                curveTo(4.394f, 18f, 3.351f, 16.161f, 4.448f, 14.872f)
                curveTo(5.745f, 13.348f, 5.548f, 10.846f, 5.76f, 8.975f)
                curveTo(6.002f, 6.851f, 7.588f, 5.055f, 9.73f, 4.322f)
                curveTo(9.73f, 3.068f, 10.746f, 2f, 12f, 2f)
                curveTo(13.254f, 2f, 14.3f, 3.079f, 14.3f, 4.332f)
                curveTo(14.28f, 4.325f, 14.32f, 4.339f, 14.3f, 4.332f)
                curveTo(12.131f, 7.382f, 14.724f, 11.909f, 18.523f, 11.47f)
                curveTo(18.661f, 12.691f, 18.714f, 13.887f, 19.552f, 14.872f)
                curveTo(20.649f, 16.161f, 19.606f, 18f, 17.777f, 18f)
                close()
            }
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(20.998f, 7.077f)
                curveTo(20.998f, 7.129f, 21.001f, 7.026f, 20.998f, 7.077f)
                curveTo(20.998f, 5.42f, 19.657f, 4f, 18f, 4f)
                curveTo(16.343f, 4f, 15.001f, 5.389f, 15.001f, 7.046f)
            }
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(20.998f, 7.077f)
                curveTo(20.918f, 8.662f, 19.605f, 10f, 18f, 10f)
                curveTo(16.374f, 10f, 15.049f, 8.66f, 15.001f, 7.046f)
            }
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(14.986f, 19.997f)
                curveTo(14.529f, 21.125f, 13.356f, 22f, 12f, 22f)
                curveTo(10.644f, 22f, 9.471f, 21.125f, 9.014f, 19.997f)
                curveTo(8.995f, 19.95f, 9.014f, 20.048f, 9.014f, 19.997f)
                curveTo(9.014f, 19.764f, 9.19f, 19.5f, 9.423f, 19.5f)
                horizontalLineTo(14.576f)
                curveTo(14.81f, 19.5f, 14.986f, 19.764f, 14.986f, 19.997f)
                curveTo(15.005f, 19.95f, 14.986f, 20.048f, 14.986f, 19.997f)
                close()
            }
        }.build()

        return _NotificationBadge!!
    }

private var _NotificationBadge: ImageVector? = null
