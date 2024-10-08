package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.Share: ImageVector
    get() {
        if (_Share != null) {
            return _Share!!
        }
        _Share = ImageVector.Builder(
            name = "Outlined.Share",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(19.559f, 20.176f)
                curveTo(18.922f, 21.543f, 17.403f, 22.335f, 15.917f, 22.074f)
                curveTo(13.91f, 21.722f, 12.624f, 19.543f, 13.327f, 17.601f)
                lineTo(8.358f, 14.409f)
                curveTo(6.869f, 15.87f, 4.339f, 15.605f, 3.184f, 13.868f)
                curveTo(2.04f, 12.147f, 2.761f, 9.697f, 4.688f, 8.888f)
                curveTo(5.928f, 8.367f, 7.398f, 8.649f, 8.358f, 9.59f)
                lineTo(13.327f, 6.403f)
                curveTo(12.745f, 4.805f, 13.479f, 2.971f, 15.002f, 2.215f)
                curveTo(16.525f, 1.459f, 18.429f, 1.984f, 19.35f, 3.413f)
                curveTo(20.271f, 4.842f, 19.963f, 6.793f, 18.645f, 7.867f)
                curveTo(17.328f, 8.942f, 15.355f, 8.853f, 14.139f, 7.664f)
                lineTo(9.171f, 10.856f)
                curveTo(9.439f, 11.598f, 9.439f, 12.411f, 9.171f, 13.153f)
                lineTo(14.139f, 16.345f)
                curveTo(15.628f, 14.888f, 18.153f, 15.152f, 19.308f, 16.885f)
                curveTo(19.955f, 17.855f, 20.051f, 19.12f, 19.559f, 20.176f)
                close()
            }
        }.build()

        return _Share!!
    }

private var _Share: ImageVector? = null
