package com.egoriku.grodnoroads.foundation.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads

val GrodnoRoads.Outlined.InsideCity: ImageVector
    get() {
        if (_InsideCity != null) {
            return _InsideCity!!
        }
        _InsideCity = ImageVector.Builder(
            name = "Outlined.InsideCity",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFE676FF))) {
                moveTo(11.994f, 21.5f)
                curveTo(11.815f, 21.5f, 11.637f, 21.471f, 11.462f, 21.413f)
                curveTo(11.288f, 21.354f, 11.133f, 21.258f, 11f, 21.125f)
                curveTo(10.3f, 20.492f, 9.542f, 19.763f, 8.725f, 18.938f)
                curveTo(7.908f, 18.112f, 7.15f, 17.225f, 6.45f, 16.275f)
                curveTo(5.75f, 15.325f, 5.167f, 14.333f, 4.7f, 13.3f)
                curveTo(4.233f, 12.267f, 4f, 11.233f, 4f, 10.2f)
                curveTo(4f, 7.7f, 4.804f, 5.708f, 6.412f, 4.225f)
                curveTo(8.021f, 2.742f, 9.883f, 2f, 12f, 2f)
                curveTo(14.117f, 2f, 15.979f, 2.742f, 17.587f, 4.225f)
                curveTo(19.196f, 5.708f, 20f, 7.7f, 20f, 10.2f)
                curveTo(20f, 11.233f, 19.767f, 12.267f, 19.3f, 13.3f)
                curveTo(18.833f, 14.333f, 18.25f, 15.325f, 17.55f, 16.275f)
                curveTo(16.85f, 17.225f, 16.092f, 18.112f, 15.275f, 18.938f)
                curveTo(14.458f, 19.763f, 13.7f, 20.492f, 13f, 21.125f)
                curveTo(12.867f, 21.258f, 12.71f, 21.354f, 12.531f, 21.413f)
                curveTo(12.352f, 21.471f, 12.173f, 21.5f, 11.994f, 21.5f)
                close()
                moveTo(12.002f, 11.75f)
                curveTo(12.484f, 11.75f, 12.896f, 11.578f, 13.238f, 11.235f)
                curveTo(13.579f, 10.892f, 13.75f, 10.48f, 13.75f, 9.998f)
                curveTo(13.75f, 9.516f, 13.578f, 9.104f, 13.235f, 8.762f)
                curveTo(12.892f, 8.421f, 12.48f, 8.25f, 11.998f, 8.25f)
                curveTo(11.516f, 8.25f, 11.104f, 8.422f, 10.762f, 8.765f)
                curveTo(10.421f, 9.108f, 10.25f, 9.52f, 10.25f, 10.002f)
                curveTo(10.25f, 10.484f, 10.422f, 10.896f, 10.765f, 11.238f)
                curveTo(11.108f, 11.579f, 11.52f, 11.75f, 12.002f, 11.75f)
                close()
            }
        }.build()

        return _InsideCity!!
    }

private var _InsideCity: ImageVector? = null
