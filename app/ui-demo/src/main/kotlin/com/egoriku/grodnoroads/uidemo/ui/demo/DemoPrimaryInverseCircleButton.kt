package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.ArrowRight
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryInverseCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size.Large
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoPrimaryInverseCircleButton(modifier: Modifier = Modifier) {
    UIDemoContainer(modifier = modifier, name = "PrimaryInverseCircleButton") {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "large", style = MaterialTheme.typography.titleSmall)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PrimaryInverseCircleButton(onClick = { }, size = Large) {
                        Icon(
                            imageVector = GrodnoRoads.Outlined.ArrowRight,
                            contentDescription = null
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "small", style = MaterialTheme.typography.titleSmall)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PrimaryInverseCircleButton(onClick = { }, size = Size.Small) {
                        Icon(
                            imageVector = GrodnoRoads.Outlined.ArrowRight,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun DemoPrimaryInverseCircleButtonPreview() = GrodnoRoadsM3ThemePreview {
    DemoPrimaryInverseCircleButton()
}
