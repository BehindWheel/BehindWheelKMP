package com.egoriku.grodnoroads.uidemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ModeNight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3Theme
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.DisabledText
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryCircleButton

class MainActivity : ComponentActivity() {

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
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "UI Demo App")
                Spacer(Modifier.weight(1f))
                IconButton(onClick = onThemeChange) {
                    Icon(imageVector = Icons.Default.ModeNight, contentDescription = null)
                }
            }
        }
        item {
            Column(
                Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray)
                    .padding(16.dp)
            ) {
                Text(text = "Normal text")
                DisabledText(text = "Disabled text")
            }
        }
        item {
            Column(
                Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray)
                    .padding(16.dp)
            ) {
                Text(text = "PrimaryCircleButton", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PrimaryCircleButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null
                        )
                    }
                    PrimaryCircleButton(onClick = { }, enabled = false) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun ComponentsPreview() = GrodnoRoadsM3ThemePreview {
    Components {

    }
}
