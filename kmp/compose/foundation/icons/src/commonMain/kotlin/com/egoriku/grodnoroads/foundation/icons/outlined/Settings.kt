package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.Settings: ImageVector
    get() {
        if (_Settings != null) {
            return _Settings!!
        }
        _Settings = ImageVector.Builder(
            name = "Outlined.Settings",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(20.835f, 10.35f)
                arcToRelative(0.642f, 0.642f, 0f, false, false, -0.313f, -0.428f)
                lineToRelative(-1.255f, -0.705f)
                arcToRelative(2.25f, 2.25f, 0f, false, true, -1.147f, -1.953f)
                lineToRelative(-0.005f, -1.356f)
                arcToRelative(0.638f, 0.638f, 0f, false, false, -0.227f, -0.483f)
                arcToRelative(9.025f, 9.025f, 0f, false, false, -2.95f, -1.639f)
                arcToRelative(0.657f, 0.657f, 0f, false, false, -0.52f, 0.047f)
                lineToRelative(-1.33f, 0.734f)
                arcToRelative(2.25f, 2.25f, 0f, false, true, -2.174f, 0f)
                lineTo(9.58f, 3.83f)
                arcToRelative(0.657f, 0.657f, 0f, false, false, -0.52f, -0.047f)
                curveToRelative(-1.08f, 0.36f, -2.08f, 0.918f, -2.948f, 1.646f)
                arcToRelative(0.638f, 0.638f, 0f, false, false, -0.227f, 0.481f)
                lineTo(5.88f, 7.27f)
                arcToRelative(2.25f, 2.25f, 0f, false, true, -1.147f, 1.95f)
                lineToRelative(-1.256f, 0.707f)
                arcToRelative(0.642f, 0.642f, 0f, false, false, -0.312f, 0.428f)
                arcToRelative(8.337f, 8.337f, 0f, false, false, 0f, 3.297f)
                arcToRelative(0.642f, 0.642f, 0f, false, false, 0.312f, 0.428f)
                lineToRelative(1.255f, 0.706f)
                arcToRelative(2.25f, 2.25f, 0f, false, true, 1.147f, 1.953f)
                lineToRelative(0.005f, 1.356f)
                curveToRelative(0f, 0.184f, 0.085f, 0.364f, 0.227f, 0.483f)
                arcToRelative(9.025f, 9.025f, 0f, false, false, 2.95f, 1.639f)
                curveToRelative(0.17f, 0.057f, 0.363f, 0.04f, 0.52f, -0.047f)
                lineToRelative(1.332f, -0.736f)
                arcToRelative(2.25f, 2.25f, 0f, false, true, 2.176f, 0f)
                lineToRelative(1.332f, 0.735f)
                arcToRelative(0.66f, 0.66f, 0f, false, false, 0.52f, 0.046f)
                arcToRelative(9.04f, 9.04f, 0f, false, false, 2.947f, -1.645f)
                arcToRelative(0.638f, 0.638f, 0f, false, false, 0.227f, -0.48f)
                lineToRelative(0.007f, -1.36f)
                arcToRelative(2.25f, 2.25f, 0f, false, true, 1.146f, -1.952f)
                lineToRelative(1.256f, -0.706f)
                curveToRelative(0.16f, -0.09f, 0.277f, -0.25f, 0.313f, -0.428f)
                arcToRelative(8.339f, 8.339f, 0f, false, false, -0.002f, -3.293f)
                close()
                moveTo(12f, 15.173f)
                curveToRelative(-2f, 0f, -3.538f, -1.878f, -3.153f, -3.791f)
                curveToRelative(0.251f, -1.245f, 1.264f, -2.245f, 2.526f, -2.493f)
                curveToRelative(1.96f, -0.385f, 3.84f, 1.16f, 3.84f, 3.111f)
                curveToRelative(0f, 1.728f, -1.463f, 3.173f, -3.213f, 3.173f)
                close()
            }
        }.build()

        return _Settings!!
    }

private var _Settings: ImageVector? = null
