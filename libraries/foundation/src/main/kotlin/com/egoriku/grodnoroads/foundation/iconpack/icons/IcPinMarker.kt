package com.egoriku.grodnoroads.foundation.iconpack.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.iconpack.GrodnoRoadsIcons

val GrodnoRoadsIcons.icPinMarker: ImageVector
    get() {
        if (_pinMarker != null) {
            return _pinMarker!!
        }
        _pinMarker = Builder(
            name = "pinMarker",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.0f, 24.0f)
                curveToRelative(0.2f, 0.0f, 0.4f, -0.2f, 0.4f, -0.4f)
                verticalLineTo(10.5f)
                curveToRelative(2.9f, -0.2f, 5.1f, -2.8f, 4.8f, -5.7f)
                reflectiveCurveTo(14.5f, -0.2f, 11.6f, 0.0f)
                reflectiveCurveTo(6.5f, 2.8f, 6.8f, 5.7f)
                curveTo(7.0f, 8.3f, 9.0f, 10.3f, 11.6f, 10.5f)
                verticalLineToRelative(13.1f)
                curveTo(11.6f, 23.8f, 11.8f, 24.0f, 12.0f, 24.0f)
                close()
                moveTo(8.9f, 4.6f)
                curveToRelative(0.2f, -1.1f, 1.0f, -2.0f, 2.1f, -2.3f)
                curveToRelative(0.2f, -0.1f, 0.5f, 0.0f, 0.5f, 0.3f)
                curveToRelative(0.1f, 0.2f, 0.0f, 0.5f, -0.3f, 0.5f)
                lineToRelative(0.0f, 0.0f)
                curveTo(10.5f, 3.3f, 9.9f, 4.0f, 9.7f, 4.8f)
                curveToRelative(0.0f, 0.2f, -0.2f, 0.3f, -0.4f, 0.3f)
                curveToRelative(0.0f, 0.0f, -0.1f, 0.0f, -0.1f, 0.0f)
                curveTo(9.0f, 5.0f, 8.8f, 4.8f, 8.9f, 4.6f)
                curveTo(8.9f, 4.6f, 8.9f, 4.6f, 8.9f, 4.6f)
                close()
            }
        }.build()
        return _pinMarker!!
    }

private var _pinMarker: ImageVector? = null
