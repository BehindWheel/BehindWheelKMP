package com.egoriku.grodnoroads.roadicons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.RoadIcons

val RoadIcons.IcTelegram: ImageVector by lazy {
    Builder(
        name = "IcTelegram",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 48.0f,
        viewportHeight = 48.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF29b6f6)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(24.0f, 4.0f)
            arcTo(
                horizontalEllipseRadius = 20.0f,
                verticalEllipseRadius = 20.0f,
                theta = 0.0f,
                isMoreThanHalf = true,
                isPositiveArc = false,
                x1 = 24.0f,
                y1 = 44.0f
            )
            arcTo(
                horizontalEllipseRadius = 20.0f,
                verticalEllipseRadius = 20.0f,
                theta = 0.0f,
                isMoreThanHalf = true,
                isPositiveArc = false,
                x1 = 24.0f,
                y1 = 4.0f
            )
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFFFFF)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(33.95f, 15.0f)
            lineToRelative(-3.746f, 19.126f)
            curveToRelative(0.0f, 0.0f, -0.161f, 0.874f, -1.245f, 0.874f)
            curveToRelative(-0.576f, 0.0f, -0.873f, -0.274f, -0.873f, -0.274f)
            lineToRelative(-8.114f, -6.733f)
            lineToRelative(-3.97f, -2.001f)
            lineToRelative(-5.095f, -1.355f)
            curveToRelative(0.0f, 0.0f, -0.907f, -0.262f, -0.907f, -1.012f)
            curveToRelative(0.0f, -0.625f, 0.933f, -0.923f, 0.933f, -0.923f)
            lineToRelative(21.316f, -8.468f)
            curveToRelative(-0.001f, -0.001f, 0.651f, -0.235f, 1.126f, -0.234f)
            curveTo(33.667f, 14.0f, 34.0f, 14.125f, 34.0f, 14.5f)
            curveTo(34.0f, 14.75f, 33.95f, 15.0f, 33.95f, 15.0f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFb0bec5)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(23.0f, 30.505f)
            lineToRelative(-3.426f, 3.374f)
            curveToRelative(0.0f, 0.0f, -0.149f, 0.115f, -0.348f, 0.12f)
            curveToRelative(-0.069f, 0.002f, -0.143f, -0.009f, -0.219f, -0.043f)
            lineToRelative(0.964f, -5.965f)
            lineTo(23.0f, 30.505f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFcfd8dc)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(29.897f, 18.196f)
            curveToRelative(-0.169f, -0.22f, -0.481f, -0.26f, -0.701f, -0.093f)
            lineTo(16.0f, 26.0f)
            curveToRelative(0.0f, 0.0f, 2.106f, 5.892f, 2.427f, 6.912f)
            curveToRelative(0.322f, 1.021f, 0.58f, 1.045f, 0.58f, 1.045f)
            lineToRelative(0.964f, -5.965f)
            lineToRelative(9.832f, -9.096f)
            curveTo(30.023f, 18.729f, 30.064f, 18.416f, 29.897f, 18.196f)
            close()
        }
    }.build()
}
