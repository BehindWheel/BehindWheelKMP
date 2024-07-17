package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.CheckCircle: ImageVector
    get() {
        if (_CheckCircle != null) {
            return _CheckCircle!!
        }
        _CheckCircle = ImageVector.Builder(
            name = "Outlined.CheckCircle",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(12f, 2f)
                curveTo(5.79f, 2f, 0.98f, 7.86f, 2.192f, 13.95f)
                curveTo(3.403f, 20.04f, 10.09f, 23.615f, 15.826f, 21.238f)
                curveTo(19.524f, 19.707f, 22f, 16.001f, 22f, 12f)
                curveTo(21.991f, 6.556f, 17.443f, 2.008f, 12f, 2f)
                close()
                moveTo(16.709f, 10.71f)
                lineTo(11.71f, 15.71f)
                curveTo(11.321f, 16.091f, 10.679f, 16.091f, 10.29f, 15.71f)
                lineTo(7.29f, 12.71f)
                curveTo(6.904f, 12.324f, 6.901f, 11.678f, 7.29f, 11.29f)
                curveTo(7.676f, 10.903f, 8.323f, 10.903f, 8.71f, 11.29f)
                lineTo(11f, 13.59f)
                lineTo(15.29f, 9.29f)
                curveTo(15.676f, 8.903f, 16.323f, 8.903f, 16.709f, 9.29f)
                curveTo(17.096f, 9.676f, 17.096f, 10.323f, 16.709f, 10.71f)
                close()
            }
        }.build()

        return _CheckCircle!!
    }

private var _CheckCircle: ImageVector? = null
