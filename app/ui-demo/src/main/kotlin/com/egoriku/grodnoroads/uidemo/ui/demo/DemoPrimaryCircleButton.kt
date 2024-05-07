package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size.Large
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size.Small
import com.egoriku.grodnoroads.shared.resources.MR
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun DemoPrimaryCircleButton() {
    UIDemoContainer(name = "PrimaryCircleButton") {
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "large", style = MaterialTheme.typography.titleSmall)
                PrimaryCircleButton(size = Large, onClick = { }) {
                    Icon(
                        painter = painterResource(MR.images.ic_arrow_right),
                        contentDescription = null
                    )
                }
                PrimaryCircleButton(size = Large, onClick = { }, enabled = false) {
                    Icon(
                        painter = painterResource(MR.images.ic_arrow_right),
                        contentDescription = null
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "small", style = MaterialTheme.typography.titleSmall)
                PrimaryCircleButton(size = Small, onClick = { }) {
                    Icon(
                        painter = painterResource(MR.images.ic_arrow_right),
                        contentDescription = null
                    )
                }
                PrimaryCircleButton(size = Small, onClick = { }, enabled = false) {
                    Icon(
                        painter = painterResource(MR.images.ic_arrow_right),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoPrimaryCircleButtonPreview() = GrodnoRoadsM3ThemePreview {
    DemoPrimaryCircleButton()
}