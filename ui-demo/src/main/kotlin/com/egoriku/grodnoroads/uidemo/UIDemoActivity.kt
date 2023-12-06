package com.egoriku.grodnoroads.uidemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
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
                    Column {
                        Header(modifier = Modifier.padding(horizontal = 16.dp)) { isDark = !isDark }
                        DemoComponents()
                    }
                }
            }
        }
    }
}

@Composable
private fun DemoComponents() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { DemoText() }
        item { DemoPrimaryButton() }
        item { DemoPrimaryCircleButton() }
        item { DemoSecondaryButton() }
        item { DemoSecondaryCircleButton() }
        item { DemoRadioButton() }
        item { DemoCheckbox() }
        item { DemoTriStateCheckbox() }
        item { DemoSwitch() }
        item { DemoNavigationRail() }
        item { DemoNavigationBar() }
    }
}

@Preview(device = "spec:width=1080px,height=5000px,dpi=440")
@Composable
private fun ComponentsPreview() = GrodnoRoadsM3ThemePreview {
    DemoComponents()
}
