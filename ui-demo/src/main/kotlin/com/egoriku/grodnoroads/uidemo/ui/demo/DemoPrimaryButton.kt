package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryButton
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoPrimaryButton() {
    UIDemoContainer(name = "PrimaryButton") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PrimaryButton(
                modifier = Modifier.weight(1f),
                onClick = { }
            ) {
                Text(text = "Enabled state")
            }
            PrimaryButton(
                modifier = Modifier.weight(1f),
                onClick = { },
                enabled = false
            ) {
                Text(text = "Disabled state")
            }
        }
    }
}