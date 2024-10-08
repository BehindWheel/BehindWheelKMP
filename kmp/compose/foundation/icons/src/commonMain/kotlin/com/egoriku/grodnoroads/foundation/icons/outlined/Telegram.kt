package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.Telegram: ImageVector
    get() {
        if (_Telegram != null) {
            return _Telegram!!
        }
        _Telegram = ImageVector.Builder(
            name = "Outlined.Telegram",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(22.208f, 2.455f)
                curveTo(21.975f, 2.255f, 21.635f, 2.196f, 21.349f, 2.308f)
                lineTo(2.349f, 9.743f)
                curveTo(1.803f, 9.956f, 1.452f, 10.52f, 1.505f, 11.104f)
                curveTo(1.557f, 11.689f, 2.001f, 12.182f, 2.577f, 12.294f)
                lineTo(7.5f, 13.261f)
                verticalLineTo(18.75f)
                curveTo(7.498f, 19.348f, 7.882f, 19.918f, 8.437f, 20.14f)
                curveTo(8.992f, 20.367f, 9.665f, 20.223f, 10.079f, 19.791f)
                lineTo(12.453f, 17.329f)
                lineTo(16.219f, 20.625f)
                curveTo(16.613f, 20.974f, 17.174f, 21.087f, 17.673f, 20.926f)
                curveTo(18.168f, 20.769f, 18.559f, 20.342f, 18.673f, 19.836f)
                lineTo(22.478f, 3.281f)
                curveTo(22.546f, 2.983f, 22.439f, 2.656f, 22.208f, 2.455f)
                close()
                moveTo(17.206f, 19.5f)
                lineTo(9.455f, 12.703f)
                lineTo(20.611f, 4.707f)
                lineTo(17.206f, 19.5f)
                close()
            }
        }.build()

        return _Telegram!!
    }

private var _Telegram: ImageVector? = null
