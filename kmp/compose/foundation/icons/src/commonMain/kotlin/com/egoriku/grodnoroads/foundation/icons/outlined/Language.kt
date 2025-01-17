package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.Language: ImageVector
    get() {
        if (_Language != null) {
            return _Language!!
        }
        _Language = ImageVector.Builder(
            name = "Outlined.Language",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(11.99f, 2f)
                curveTo(6.47f, 2f, 2f, 6.48f, 2f, 12f)
                curveTo(2f, 17.52f, 6.47f, 22f, 11.99f, 22f)
                curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
                curveTo(22f, 6.48f, 17.52f, 2f, 11.99f, 2f)
                close()
                moveTo(18.92f, 8f)
                lineTo(15.97f, 8f)
                curveTo(15.65f, 6.75f, 15.19f, 5.55f, 14.59f, 4.44f)
                curveTo(16.43f, 5.07f, 17.96f, 6.35f, 18.92f, 8f)
                close()
                moveTo(12f, 4.04f)
                curveTo(12.83f, 5.24f, 13.48f, 6.57f, 13.91f, 8f)
                lineTo(10.09f, 8f)
                curveTo(10.52f, 6.57f, 11.17f, 5.24f, 12f, 4.04f)
                close()
                moveTo(4.26f, 14f)
                curveTo(4.1f, 13.36f, 4f, 12.69f, 4f, 12f)
                curveTo(4f, 11.31f, 4.1f, 10.64f, 4.26f, 10f)
                lineTo(7.64f, 10f)
                curveTo(7.56f, 10.66f, 7.5f, 11.32f, 7.5f, 12f)
                curveTo(7.5f, 12.68f, 7.56f, 13.34f, 7.64f, 14f)
                lineTo(4.26f, 14f)
                close()
                moveTo(5.08f, 16f)
                lineTo(8.03f, 16f)
                curveTo(8.35f, 17.25f, 8.81f, 18.45f, 9.41f, 19.56f)
                curveTo(7.57f, 18.93f, 6.04f, 17.66f, 5.08f, 16f)
                close()
                moveTo(8.03f, 8f)
                lineTo(5.08f, 8f)
                curveTo(6.04f, 6.34f, 7.57f, 5.07f, 9.41f, 4.44f)
                curveTo(8.81f, 5.55f, 8.35f, 6.75f, 8.03f, 8f)
                close()
                moveTo(12f, 19.96f)
                curveTo(11.17f, 18.76f, 10.52f, 17.43f, 10.09f, 16f)
                lineTo(13.91f, 16f)
                curveTo(13.48f, 17.43f, 12.83f, 18.76f, 12f, 19.96f)
                close()
                moveTo(14.34f, 14f)
                lineTo(9.66f, 14f)
                curveTo(9.57f, 13.34f, 9.5f, 12.68f, 9.5f, 12f)
                curveTo(9.5f, 11.32f, 9.57f, 10.65f, 9.66f, 10f)
                lineTo(14.34f, 10f)
                curveTo(14.43f, 10.65f, 14.5f, 11.32f, 14.5f, 12f)
                curveTo(14.5f, 12.68f, 14.43f, 13.34f, 14.34f, 14f)
                close()
                moveTo(14.59f, 19.56f)
                curveTo(15.19f, 18.45f, 15.65f, 17.25f, 15.97f, 16f)
                lineTo(18.92f, 16f)
                curveTo(17.96f, 17.65f, 16.43f, 18.93f, 14.59f, 19.56f)
                close()
                moveTo(16.36f, 14f)
                curveTo(16.44f, 13.34f, 16.5f, 12.68f, 16.5f, 12f)
                curveTo(16.5f, 11.32f, 16.44f, 10.66f, 16.36f, 10f)
                lineTo(19.74f, 10f)
                curveTo(19.9f, 10.64f, 20f, 11.31f, 20f, 12f)
                curveTo(20f, 12.69f, 19.9f, 13.36f, 19.74f, 14f)
                lineTo(16.36f, 14f)
                close()
            }
        }.build()

        return _Language!!
    }

private var _Language: ImageVector? = null
