package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.Faq: ImageVector
    get() {
        if (_Faq != null) {
            return _Faq!!
        }
        _Faq = ImageVector.Builder(
            name = "Outlined.Faq",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(12f, 2.25f)
                curveTo(5.935f, 2.25f, 1.268f, 8.021f, 2.437f, 13.902f)
                curveTo(3.199f, 17.729f, 6.271f, 20.802f, 10.098f, 21.563f)
                curveTo(16.046f, 22.746f, 21.75f, 17.997f, 21.75f, 12f)
                curveTo(21.744f, 6.692f, 17.308f, 2.256f, 12f, 2.25f)
                close()
                moveTo(12f, 18f)
                curveTo(11.555f, 18f, 11.131f, 17.717f, 10.961f, 17.306f)
                curveTo(10.693f, 16.66f, 11.095f, 15.908f, 11.781f, 15.772f)
                curveTo(12.466f, 15.635f, 13.125f, 16.177f, 13.125f, 16.875f)
                curveTo(13.125f, 17.488f, 12.613f, 18f, 12f, 18f)
                close()
                moveTo(12.75f, 13.466f)
                curveTo(12.75f, 13.665f, 12.671f, 13.89f, 12.53f, 14.03f)
                curveTo(12.242f, 14.319f, 11.759f, 14.319f, 11.47f, 14.03f)
                curveTo(11.146f, 13.706f, 11.25f, 13.165f, 11.25f, 12.75f)
                curveTo(11.25f, 12.342f, 11.592f, 12f, 12f, 12f)
                curveTo(13.24f, 12f, 14.25f, 11.156f, 14.25f, 10.125f)
                curveTo(14.25f, 9.094f, 13.24f, 8.25f, 12f, 8.25f)
                curveTo(10.76f, 8.25f, 9.75f, 9.094f, 9.75f, 10.125f)
                curveTo(9.75f, 10.638f, 9.663f, 11.25f, 9f, 11.25f)
                curveTo(8.337f, 11.25f, 8.25f, 10.638f, 8.25f, 10.125f)
                curveTo(8.25f, 8.264f, 9.932f, 6.75f, 12f, 6.75f)
                curveTo(14.068f, 6.75f, 15.75f, 8.264f, 15.75f, 10.125f)
                curveTo(15.75f, 11.754f, 14.46f, 13.152f, 12.75f, 13.466f)
                close()
            }
        }.build()

        return _Faq!!
    }

private var _Faq: ImageVector? = null
