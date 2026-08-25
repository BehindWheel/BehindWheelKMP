package com.egoriku.grodnoroads.settings.debugtools.screen.uikit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.common.ui.SettingsTopBar
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Moon
import com.egoriku.grodnoroads.foundation.layout.Spacer
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3Theme
import com.egoriku.grodnoroads.foundation.theme.LocalPlatform
import com.egoriku.grodnoroads.foundation.theme.Platform
import com.egoriku.grodnoroads.foundation.theme.Platform.Android
import com.egoriku.grodnoroads.settings.debugtools.screen.uikit.ui.UiDemoList

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UIKitScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    var isDarkTheme by rememberMutableState { false }

    GrodnoRoadsM3Theme(isDarkTheme) {
        Scaffold(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.surface,
            topBar = {
                SettingsTopBar(
                    title = "UIKit",
                    onBack = onBack,
                    actions = {
                        IconButton(onClick = { isDarkTheme = !isDarkTheme }) {
                            Icon(
                                imageVector = GrodnoRoads.Outlined.Moon,
                                contentDescription = null
                            )
                        }
                    }
                )
            },
            contentWindowInsets = WindowInsets()
        ) { paddingValues ->
            val current = LocalPlatform.current
            var platform by rememberMutableState { current }

            CompositionLocalProvider(LocalPlatform provides platform) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(8.dp)
                    PlatformSegmentedRow(
                        modifier = Modifier.fillMaxWidth(),
                        current = platform,
                        onPlatformChange = { platform = it }
                    )
                    Spacer(16.dp)
                    UiDemoList()
                }
            }
        }
    }
}

@Composable
private fun PlatformSegmentedRow(
    current: Platform,
    modifier: Modifier = Modifier,
    onPlatformChange: (Platform) -> Unit
) {
    SingleChoiceSegmentedButtonRow(modifier) {
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
            onClick = {
                onPlatformChange(Android)
            },
            selected = current == Android,
            label = {
                Text(text = "Android")
            }
        )
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
            onClick = {
                onPlatformChange(Platform.IOS)
            },
            selected = current == Platform.IOS,
            label = {
                Text(text = "iOS")
            }
        )
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun UIKitScreenPreview() = GrodnoRoadsM3ThemePreview {
    UIKitScreen(onBack = {})
}
