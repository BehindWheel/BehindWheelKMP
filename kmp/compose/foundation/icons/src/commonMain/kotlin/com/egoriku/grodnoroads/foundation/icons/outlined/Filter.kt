package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.Filter: ImageVector
    get() {
        if (_Filter != null) {
            return _Filter!!
        }
        _Filter = ImageVector.Builder(
            name = "Outlined.Filter",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(6.8f, 9.32f)
                lineTo(10.35f, 3.63f)
                curveTo(10.76f, 2.96f, 10.28f, 2.1f, 9.5f, 2.1f)
                horizontalLineTo(5.4f)
                curveTo(4.3f, 2.1f, 3.4f, 3f, 3.4f, 4.2f)
                verticalLineTo(6.5f)
                curveTo(3.4f, 7.752f, 4.439f, 8.646f, 5.23f, 9.48f)
                curveTo(5.68f, 9.95f, 6.46f, 9.87f, 6.8f, 9.32f)
                close()
            }
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(20.6f, 4.1f)
                curveTo(20.6f, 3f, 19.7f, 2.1f, 18.6f, 2.1f)
                curveTo(17.351f, 2.1f, 13f, 1.5f, 12.63f, 2.57f)
                lineTo(7.51f, 10.79f)
                curveTo(7.26f, 11.18f, 7.32f, 11.68f, 7.63f, 12.01f)
                curveTo(8.31f, 12.719f, 8.9f, 13.568f, 8.9f, 14.6f)
                verticalLineTo(19.9f)
                curveTo(8.9f, 21.5f, 10.7f, 22.4f, 12f, 21.6f)
                curveTo(12.988f, 20.965f, 14.3f, 20.373f, 14.3f, 19f)
                curveTo(14.3f, 17.107f, 14f, 13.5f, 15.3f, 12.4f)
                lineTo(19.6f, 8.6f)
                curveTo(20.817f, 7.383f, 20.6f, 5.667f, 20.6f, 4.1f)
                close()
            }
        }.build()

        return _Filter!!
    }

private var _Filter: ImageVector? = null
