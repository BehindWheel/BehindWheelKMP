package com.egoriku.grodnoroads.settings.debugtools

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
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
import com.egoriku.grodnoroads.foundation.common.ui.bottomsheet.BasicModalBottomSheet
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.filled.Edit
import com.egoriku.grodnoroads.foundation.icons.outlined.Appearance
import com.egoriku.grodnoroads.foundation.icons.outlined.Moon
import com.egoriku.grodnoroads.foundation.layout.Spacer
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3Theme
import com.egoriku.grodnoroads.foundation.theme.LocalPlatform
import com.egoriku.grodnoroads.foundation.theme.Platform
import com.egoriku.grodnoroads.foundation.theme.Platform.Android
import com.egoriku.grodnoroads.settings.debugtools.domain.DebugToolsComponent
import com.egoriku.grodnoroads.settings.debugtools.ui.datastore.DataStoreEdit
import com.egoriku.grodnoroads.settings.debugtools.ui.palette.Material3Palette
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.UiDemoList
import com.egoriku.grodnoroads.specialevent.domain.model.EventType
import com.egoriku.grodnoroads.specialevent.screen.SpecialEventDialog
import org.jetbrains.compose.resources.stringResource

internal sealed interface SheetType {
    data object NoSheet : SheetType
    data object DataStoreEdit : SheetType
    data object Material3Palette : SheetType
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugToolsScreen(
    debugToolsComponent: DebugToolsComponent,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    var sheetType by rememberMutableState<SheetType> { SheetType.NoSheet }
    var isDarkTheme by rememberMutableState { false }
    var specialEventType by rememberMutableState<EventType?> { null }
    val showMapDebugOverlay by debugToolsComponent.showMapDebugOverlay.collectAsState()

    GrodnoRoadsM3Theme(isDarkTheme) {
        Scaffold(
            modifier = modifier,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                SettingsTopBar(
                    title = stringResource(Res.string.settings_section_debug_tools),
                    onBack = onBack
                )
            }
        ) {
            val current = LocalPlatform.current
            var platform by rememberMutableState { current }
            val scrollState = rememberScrollState()

            CompositionLocalProvider(LocalPlatform provides platform) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(it)
                ) {
                    Spacer(8.dp)
                    AnimatedVisibility(visible = scrollState.isScrollingUp()) {
                        Column {
                            TopActions(
                                onDataStoreEdit = { sheetType = SheetType.DataStoreEdit },
                                onOpenPalette = { sheetType = SheetType.Material3Palette },
                                onChangeDarkTheme = { isDarkTheme = !isDarkTheme },
                                onShowSpecialEvent = { specialEventType = it }
                            )
                            Spacer(16.dp)
                        }
                    }
                    PlatformSegmentedRow(
                        current = platform,
                        onPlatformChange = { platform = it }
                    )
                    Spacer(4.dp)
                    UiDemoList(modifier = Modifier.verticalScroll(scrollState))
                }
            }

            specialEventType?.let { eventType ->
                SpecialEventDialog(
                    eventType = eventType,
                    onClose = { _ -> specialEventType = null }
                )
            }

            if (sheetType != SheetType.NoSheet) {
                BasicModalBottomSheet(
                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                    onDismissRequest = { sheetType = SheetType.NoSheet },
                    content = {
                        when (sheetType) {
                            SheetType.DataStoreEdit -> {
                                DataStoreEdit(
                                    modifier = Modifier.padding(bottom = 16.dp),
                                    showMapDebugOverlay = showMapDebugOverlay,
                                    onShowMapDebugOverlayChange = debugToolsComponent::setShowMapDebugOverlay,
                                    resetOnboarding = debugToolsComponent::resetOnboarding,
                                    resetReportingLimit = debugToolsComponent::resetReportingLimit
                                )
                            }
                            SheetType.Material3Palette -> Material3Palette()
                            SheetType.NoSheet -> error("Unexpected sheet type")
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun TopActions(
    onDataStoreEdit: () -> Unit,
    onOpenPalette: () -> Unit,
    onChangeDarkTheme: () -> Unit,
    onShowSpecialEvent: (EventType) -> Unit
) {
    Card {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = onDataStoreEdit) {
                Icon(
                    imageVector = GrodnoRoads.Filled.Edit,
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
            IconButton(onClick = { onShowSpecialEvent(EventType.Spring) }) {
                Text(text = "🌸")
            }
            IconButton(onClick = { onShowSpecialEvent(EventType.Autumn) }) {
                Text(text = "🍁")
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

@Composable
private fun ScrollState.isScrollingUp(scrollOffset: Int = 100): Boolean {
    var lastScroll by rememberMutableState { 0 }

    return remember {
        derivedStateOf {
            val current = value

            when {
                current > lastScroll + scrollOffset -> {
                    lastScroll = current - scrollOffset
                    false
                }
                current < lastScroll - scrollOffset -> {
                    lastScroll = current + scrollOffset
                    true
                }
                current >= lastScroll + scrollOffset -> false
                current < lastScroll -> true
                else -> lastScroll - scrollOffset < 0 // initial state
            }
        }
    }.value
}
