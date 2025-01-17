package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.Ok: ImageVector
    get() {
        if (_Ok != null) {
            return _Ok!!
        }
        _Ok = ImageVector.Builder(
            name = "Outlined.Ok",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(10.302f, 19.513f)
                curveTo(9.662f, 20.163f, 8.626f, 20.163f, 7.986f, 19.513f)
                lineTo(3.48f, 14.931f)
                curveTo(2.84f, 14.281f, 2.84f, 13.227f, 3.48f, 12.577f)
                lineTo(3.552f, 12.504f)
                curveTo(4.191f, 11.854f, 5.228f, 11.854f, 5.867f, 12.504f)
                lineTo(9.144f, 15.835f)
                lineTo(18.866f, 5.513f)
                curveTo(19.461f, 4.882f, 20.436f, 4.826f, 21.097f, 5.386f)
                lineTo(21.411f, 5.652f)
                curveTo(22.15f, 6.278f, 22.201f, 7.415f, 21.52f, 8.108f)
                lineTo(10.302f, 19.513f)
                close()
            }
        }.build()

        return _Ok!!
    }

private var _Ok: ImageVector? = null
