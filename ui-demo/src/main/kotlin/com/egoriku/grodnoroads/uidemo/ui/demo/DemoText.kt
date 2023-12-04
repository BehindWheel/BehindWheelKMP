package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.foundation.uikit.DisabledText
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoText() {
    UIDemoContainer(name = "Text") {
        Text(text = "Normal text")
        DisabledText(text = "Disabled text")
    }
}