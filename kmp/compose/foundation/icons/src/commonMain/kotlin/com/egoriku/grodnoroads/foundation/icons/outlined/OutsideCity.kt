package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.OutsideCity: ImageVector
    get() {
        if (_OutsideCity != null) {
            return _OutsideCity!!
        }
        _OutsideCity = ImageVector.Builder(
            name = "Outlined.OutsideCity",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(12f, 21.5f)
                curveTo(11.817f, 21.5f, 11.637f, 21.471f, 11.462f, 21.413f)
                curveTo(11.288f, 21.354f, 11.133f, 21.258f, 11f, 21.125f)
                curveTo(10.3f, 20.492f, 9.542f, 19.763f, 8.725f, 18.938f)
                curveTo(7.908f, 18.112f, 7.15f, 17.225f, 6.45f, 16.275f)
                curveTo(5.75f, 15.325f, 5.167f, 14.333f, 4.7f, 13.3f)
                curveTo(4.233f, 12.267f, 4f, 11.233f, 4f, 10.2f)
                curveTo(4f, 9.567f, 4.054f, 8.971f, 4.162f, 8.413f)
                curveTo(4.271f, 7.854f, 4.433f, 7.325f, 4.65f, 6.825f)
                lineTo(1.175f, 3.325f)
                curveTo(1.025f, 3.175f, 0.95f, 3f, 0.95f, 2.8f)
                curveTo(0.95f, 2.6f, 1.025f, 2.425f, 1.175f, 2.275f)
                curveTo(1.325f, 2.125f, 1.504f, 2.05f, 1.712f, 2.05f)
                curveTo(1.921f, 2.05f, 2.1f, 2.125f, 2.25f, 2.275f)
                lineTo(21.375f, 21.375f)
                curveTo(21.525f, 21.525f, 21.596f, 21.704f, 21.587f, 21.913f)
                curveTo(21.579f, 22.121f, 21.5f, 22.3f, 21.35f, 22.45f)
                curveTo(21.2f, 22.6f, 21.025f, 22.675f, 20.825f, 22.675f)
                curveTo(20.625f, 22.675f, 20.45f, 22.6f, 20.3f, 22.45f)
                lineTo(16f, 18.15f)
                curveTo(15.567f, 18.633f, 15.104f, 19.125f, 14.613f, 19.625f)
                curveTo(14.121f, 20.125f, 13.583f, 20.625f, 13f, 21.125f)
                curveTo(12.867f, 21.258f, 12.712f, 21.354f, 12.538f, 21.413f)
                curveTo(12.363f, 21.471f, 12.183f, 21.5f, 12f, 21.5f)
                close()
                moveTo(17.925f, 15.775f)
                lineTo(13.3f, 11.15f)
                curveTo(13.433f, 11f, 13.542f, 10.829f, 13.625f, 10.637f)
                curveTo(13.708f, 10.446f, 13.75f, 10.233f, 13.75f, 10f)
                curveTo(13.75f, 9.517f, 13.579f, 9.104f, 13.238f, 8.762f)
                curveTo(12.896f, 8.421f, 12.483f, 8.25f, 12f, 8.25f)
                curveTo(11.767f, 8.25f, 11.554f, 8.292f, 11.363f, 8.375f)
                curveTo(11.171f, 8.458f, 11f, 8.567f, 10.85f, 8.7f)
                lineTo(6.4f, 4.25f)
                curveTo(7.15f, 3.517f, 8.008f, 2.958f, 8.975f, 2.575f)
                curveTo(9.942f, 2.192f, 10.95f, 2f, 12f, 2f)
                curveTo(14.117f, 2f, 15.979f, 2.742f, 17.587f, 4.225f)
                curveTo(19.196f, 5.708f, 20f, 7.7f, 20f, 10.2f)
                curveTo(20f, 11.05f, 19.829f, 11.938f, 19.487f, 12.863f)
                curveTo(19.146f, 13.788f, 18.625f, 14.758f, 17.925f, 15.775f)
                close()
            }
        }.build()

        return _OutsideCity!!
    }

private var _OutsideCity: ImageVector? = null
