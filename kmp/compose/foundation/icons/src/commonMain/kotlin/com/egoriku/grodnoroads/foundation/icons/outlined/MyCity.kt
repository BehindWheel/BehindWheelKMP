package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.MyCity: ImageVector
    get() {
        if (_MyCity != null) {
            return _MyCity!!
        }
        _MyCity = ImageVector.Builder(
            name = "Outlined.MyCity",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(12f, 22f)
                curveTo(10.233f, 22f, 8.792f, 21.742f, 7.675f, 21.225f)
                curveTo(6.558f, 20.708f, 6f, 20.05f, 6f, 19.25f)
                curveTo(6f, 18.8f, 6.204f, 18.375f, 6.613f, 17.975f)
                curveTo(7.021f, 17.575f, 7.583f, 17.25f, 8.3f, 17f)
                lineTo(8.75f, 18.45f)
                curveTo(8.483f, 18.533f, 8.238f, 18.65f, 8.013f, 18.8f)
                curveTo(7.788f, 18.95f, 7.608f, 19.1f, 7.475f, 19.25f)
                curveTo(7.758f, 19.583f, 8.346f, 19.875f, 9.238f, 20.125f)
                curveTo(10.129f, 20.375f, 11.05f, 20.5f, 12f, 20.5f)
                curveTo(12.95f, 20.5f, 13.875f, 20.375f, 14.775f, 20.125f)
                curveTo(15.675f, 19.875f, 16.267f, 19.583f, 16.55f, 19.25f)
                curveTo(16.417f, 19.117f, 16.242f, 18.975f, 16.025f, 18.825f)
                curveTo(15.808f, 18.675f, 15.558f, 18.55f, 15.275f, 18.45f)
                lineTo(15.7f, 17f)
                curveTo(16.417f, 17.25f, 16.979f, 17.575f, 17.388f, 17.975f)
                curveTo(17.796f, 18.375f, 18f, 18.8f, 18f, 19.25f)
                curveTo(18f, 20.05f, 17.442f, 20.708f, 16.325f, 21.225f)
                curveTo(15.208f, 21.742f, 13.767f, 22f, 12f, 22f)
                close()
                moveTo(12f, 19.35f)
                curveTo(11.8f, 19.35f, 11.625f, 19.292f, 11.475f, 19.175f)
                curveTo(11.325f, 19.058f, 11.217f, 18.908f, 11.15f, 18.725f)
                curveTo(10.75f, 17.508f, 10.25f, 16.5f, 9.65f, 15.7f)
                curveTo(9.05f, 14.9f, 8.467f, 14.133f, 7.9f, 13.4f)
                curveTo(7.333f, 12.667f, 6.85f, 11.904f, 6.45f, 11.113f)
                curveTo(6.05f, 10.321f, 5.85f, 9.333f, 5.85f, 8.15f)
                curveTo(5.85f, 6.433f, 6.446f, 4.979f, 7.638f, 3.787f)
                curveTo(8.829f, 2.596f, 10.283f, 2f, 12f, 2f)
                curveTo(13.717f, 2f, 15.171f, 2.596f, 16.363f, 3.787f)
                curveTo(17.554f, 4.979f, 18.15f, 6.433f, 18.15f, 8.15f)
                curveTo(18.15f, 9.333f, 17.95f, 10.321f, 17.55f, 11.113f)
                curveTo(17.15f, 11.904f, 16.667f, 12.667f, 16.1f, 13.4f)
                curveTo(15.533f, 14.133f, 14.95f, 14.9f, 14.35f, 15.7f)
                curveTo(13.75f, 16.5f, 13.25f, 17.508f, 12.85f, 18.725f)
                curveTo(12.783f, 18.908f, 12.675f, 19.058f, 12.525f, 19.175f)
                curveTo(12.375f, 19.292f, 12.2f, 19.35f, 12f, 19.35f)
                close()
                moveTo(12f, 9.65f)
                curveTo(12.417f, 9.65f, 12.771f, 9.504f, 13.063f, 9.212f)
                curveTo(13.354f, 8.921f, 13.5f, 8.567f, 13.5f, 8.15f)
                curveTo(13.5f, 7.733f, 13.354f, 7.379f, 13.063f, 7.088f)
                curveTo(12.771f, 6.796f, 12.417f, 6.65f, 12f, 6.65f)
                curveTo(11.583f, 6.65f, 11.229f, 6.796f, 10.938f, 7.088f)
                curveTo(10.646f, 7.379f, 10.5f, 7.733f, 10.5f, 8.15f)
                curveTo(10.5f, 8.567f, 10.646f, 8.921f, 10.938f, 9.212f)
                curveTo(11.229f, 9.504f, 11.583f, 9.65f, 12f, 9.65f)
                close()
            }
        }.build()

        return _MyCity!!
    }

private var _MyCity: ImageVector? = null
