package com.egoriku.grodnoroads.uidemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3Theme
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.uidemo.ui.Header
import com.egoriku.grodnoroads.uidemo.ui.demo.*

class UIDemoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            var isDark by rememberMutableState { false }

            GrodnoRoadsM3Theme(isDark) {
                Surface {
                    Components(onThemeChange = { isDark = !isDark })
                }
            }
        }
    }
}

@Composable
fun Components(onThemeChange: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        item { Header(onThemeChange) }
        item { DemoText() }
        item { DemoPrimaryButton() }
        item { DemoPrimaryCircleButton() }
        item { DemoSecondaryButton() }
        item { DemoSecondaryCircleButton() }
        item { DemoRadioButton() }
        item { DemoCheckbox() }
        item { DemoTriStateCheckbox() }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun ComponentsPreview() = GrodnoRoadsM3ThemePreview {
    Components {}
}
