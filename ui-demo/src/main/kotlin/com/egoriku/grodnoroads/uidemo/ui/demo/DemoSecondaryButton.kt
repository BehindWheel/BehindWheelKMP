package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.uikit.button.SecondaryButton
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoSecondaryButton() {
    UIDemoContainer(name = "SecondaryButton") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SecondaryButton(
                modifier = Modifier.weight(1f),
                onClick = { }
            ) {
                Text(text = "Enabled state")
            }
            SecondaryButton(
                modifier = Modifier.weight(1f),
                onClick = { },
                enabled = false
            ) {
                Text(text = "Disabled state")
            }
        }
    }
}