package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.ic_arrow_left
import com.egoriku.grodnoroads.compose.resources.ic_telegram
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.button.SecondaryCircleButton
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer
import org.jetbrains.compose.resources.painterResource

@Composable
fun DemoSecondaryCircleButton() {
    UIDemoContainer(name = "SecondaryCircleButton") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SecondaryCircleButton(onClick = { }) {
                Icon(
                    painter = painterResource(Res.drawable.ic_telegram),
                    contentDescription = null
                )
            }
            SecondaryCircleButton(
                onClick = { },
                enabled = false
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_left),
                    contentDescription = null
                )
            }
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoSecondaryCircleButtonPreview() = GrodnoRoadsM3ThemePreview {
    DemoSecondaryCircleButton()
}