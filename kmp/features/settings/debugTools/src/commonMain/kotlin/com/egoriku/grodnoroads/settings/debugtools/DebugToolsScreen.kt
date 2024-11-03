package com.egoriku.grodnoroads.settings.debugtools

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.settings_section_debug_tools
import com.egoriku.grodnoroads.foundation.common.ui.SettingsTopBar
import com.egoriku.grodnoroads.foundation.core.LocalPlatform
import com.egoriku.grodnoroads.foundation.core.Platform
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Appearance
import com.egoriku.grodnoroads.foundation.icons.outlined.Moon
import com.egoriku.grodnoroads.foundation.icons.outlined.ViewCarousel
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3Theme
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import com.egoriku.grodnoroads.settings.debugtools.domain.DebugToolsComponent
import com.egoriku.grodnoroads.settings.debugtools.ui.palette.Material3Palette
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.UiDemoList
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugToolsScreen(
    debugToolsComponent: DebugToolsComponent,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    var isDarkTheme by rememberMutableState { false }
    var isOpenPalette by rememberMutableState { false }

    GrodnoRoadsM3Theme(isDarkTheme) {
        Scaffold(
            modifier = modifier,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                SettingsTopBar(
                    title = stringResource(Res.string.settings_section_debug_tools),
                    onBack = onBack,
                )
            }
        ) {
            var platform by rememberMutableState { Platform.Android }
            val scrollState = rememberScrollState()

            CompositionLocalProvider(LocalPlatform provides platform) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(it)
                ) {
                    VerticalSpacer(8.dp)
                    AnimatedVisibility(visible = scrollState.isScrollingUp()) {
                        Column {
                            TopActions(
                                debugToolsComponent = debugToolsComponent,
                                onOpenPalette = { isOpenPalette = true },
                                onChangeDarkTheme = { isDarkTheme = !isDarkTheme }
                            )
                            VerticalSpacer(16.dp)
                        }
                    }
                    PlatformSegmentedRow(
                        current = platform,
                        onPlatformChange = { platform = it }
                    )
                    VerticalSpacer(4.dp)
                    UiDemoList(modifier = Modifier.verticalScroll(scrollState))
                }
            }

            if (isOpenPalette) {
                val bottomPadding =
                    WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

                ModalBottomSheet(
                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                    onDismissRequest = { isOpenPalette = false },
                    contentWindowInsets = {
                        WindowInsets(0, 0, 0, 0)
                    }
                ) {
                    Material3Palette(modifier = Modifier.padding(bottom = bottomPadding))
                }
            }
        }
    }
}

@Composable
private fun TopActions(
    debugToolsComponent: DebugToolsComponent,
    onOpenPalette: () -> Unit,
    onChangeDarkTheme: () -> Unit
) {
    Card {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = debugToolsComponent::showOnboarding) {
                Icon(
                    imageVector = GrodnoRoads.Outlined.ViewCarousel,
                    contentDescription = null
                )
            }
            IconButton(onClick = onOpenPalette) {
                Icon(
                    imageVector = GrodnoRoads.Outlined.Appearance,
                    contentDescription = null
                )
            }
            IconButton(onClick = onChangeDarkTheme) {
                Icon(
                    imageVector = GrodnoRoads.Outlined.Moon,
                    contentDescription = null
                )
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

@Composable
private fun ScrollState.isScrollingUp(scrollOffset: Int = 100): Boolean {
    var lastScroll by rememberMutableState { 0 }

    if (value > lastScroll + scrollOffset) {
        lastScroll = value - scrollOffset // scroll up
    } else if (value < lastScroll - scrollOffset) {
        lastScroll = value + scrollOffset // scroll down
    }

    val isInitialStateORScrollingUp = remember {
        derivedStateOf {
            when {
                value >= lastScroll + scrollOffset -> false
                value < lastScroll -> true
                else -> lastScroll - scrollOffset < 0 // initial state
            }
        }
    }
    return isInitialStateORScrollingUp.value
}
