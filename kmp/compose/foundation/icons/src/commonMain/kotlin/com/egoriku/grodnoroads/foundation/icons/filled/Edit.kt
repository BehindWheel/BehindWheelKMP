package com.egoriku.grodnoroads.foundation.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Filled.Edit: ImageVector
    get() {
        if (_Edit != null) {
            return _Edit!!
        }
        _Edit = ImageVector.Builder(
            name = "Filled.Edit",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF232F34))) {
                moveTo(19.02f, 3.48f)
                curveTo(16.553f, 1.013f, 13.96f, 2.71f, 11.98f, 4.69f)
                curveTo(10.239f, 6.431f, 16.632f, 11.708f, 17.82f, 10.52f)
                curveTo(19.857f, 8.466f, 21.615f, 6.049f, 19.02f, 3.48f)
                close()
            }
            path(fill = SolidColor(Color(0xFF232F34))) {
                moveTo(21f, 22f)
                horizontalLineTo(3f)
                curveTo(2.59f, 22f, 2.25f, 21.66f, 2.25f, 21.25f)
                curveTo(2.25f, 20.84f, 2.59f, 20.5f, 3f, 20.5f)
                horizontalLineTo(21f)
                curveTo(21.41f, 20.5f, 21.75f, 20.84f, 21.75f, 21.25f)
                curveTo(21.75f, 21.66f, 21.41f, 22f, 21f, 22f)
                close()
            }
            path(fill = SolidColor(Color(0xFF232F34))) {
                moveTo(15.824f, 11.58f)
                curveTo(16.162f, 11.724f, 16.26f, 12.169f, 16f, 12.429f)
                lineTo(10.279f, 18.15f)
                curveTo(9.239f, 19.276f, 5.156f, 19.459f, 4.129f, 18.46f)
                curveTo(3.102f, 17.409f, 3.352f, 13.417f, 4.439f, 12.33f)
                lineTo(10.167f, 6.602f)
                curveTo(10.425f, 6.344f, 10.86f, 6.441f, 11.007f, 6.775f)
                curveTo(11.366f, 7.586f, 11.895f, 8.376f, 12.469f, 9.05f)
                curveTo(12.799f, 9.45f, 13.169f, 9.81f, 13.499f, 10.09f)
                curveTo(14.14f, 10.731f, 14.997f, 11.227f, 15.824f, 11.58f)
                close()
            }
        }.build()

        return _Edit!!
    }

@Suppress("ObjectPropertyName")
private var _Edit: ImageVector? = null
