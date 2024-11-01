package com.egoriku.grodnoroads.settings.debugtools

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.settings_section_debug_tools
import com.egoriku.grodnoroads.foundation.common.ui.SettingsTopBar
import com.egoriku.grodnoroads.foundation.core.LocalPlatform
import com.egoriku.grodnoroads.foundation.core.Platform
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.ViewCarousel
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import com.egoriku.grodnoroads.settings.debugtools.domain.DebugToolsComponent
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.DemoSwitch
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugToolsScreen(
    debugToolsComponent: DebugToolsComponent,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SettingsTopBar(
                title = stringResource(Res.string.settings_section_debug_tools),
                onBack = onBack,
                actions = {
                    IconButton(onClick = debugToolsComponent::showOnboarding) {
                        Icon(
                            imageVector = GrodnoRoads.Outlined.ViewCarousel,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        var platform by rememberMutableState { Platform.Android }

        CompositionLocalProvider(LocalPlatform provides platform) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(it)
                    .verticalScroll(rememberScrollState())
            ) {
                VerticalSpacer(8.dp)
                PlatformSegmentedRow(
                    current = platform,
                    onPlatformChange = { platform = it }
                )
                VerticalSpacer(16.dp)
                DemoSwitch()
            }
        }
    }
}

@Composable
private fun PlatformSegmentedRow(
    current: Platform,
    onPlatformChange: (Platform) -> Unit
) {
    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
            onClick = {
                onPlatformChange(Platform.Android)
            },
            selected = current == Platform.Android,
            label = {
                Text(text = "Android")
            },
        )
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
            onClick = {
                onPlatformChange(Platform.IOS)
            },
            selected = current == Platform.IOS,
            label = {
                Text(text = "iOS")
            },
        )
    }
}
