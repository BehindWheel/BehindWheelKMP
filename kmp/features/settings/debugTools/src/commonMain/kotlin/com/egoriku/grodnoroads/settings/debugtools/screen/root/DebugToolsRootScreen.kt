package com.egoriku.grodnoroads.settings.debugtools.screen.root

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.settings_section_debug_tools
import com.egoriku.grodnoroads.foundation.common.ui.SettingsTopBar
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.settings.debugtools.domain.root.DebugToolsRootComponent
import com.egoriku.grodnoroads.settings.debugtools.domain.root.DebugToolsRootComponentPreview
import org.jetbrains.compose.resources.stringResource

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DebugToolsRootScreen(
    debugToolsRootComponent: DebugToolsRootComponent,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val features = listOf(
        DebugFeature(title = "Auth", onClick = debugToolsRootComponent::onOpenAuth),
        DebugFeature(title = "DataStore", onClick = debugToolsRootComponent::onOpenDataStoreEdit),
        DebugFeature(title = "Palette", onClick = debugToolsRootComponent::onOpenPalette),
        DebugFeature(title = "Special Events", onClick = debugToolsRootComponent::onOpenSpecialEvents),
        DebugFeature(title = "UIKit", onClick = debugToolsRootComponent::onOpenUiKit)
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            SettingsTopBar(
                title = stringResource(Res.string.settings_section_debug_tools),
                onBack = onBack
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(
                items = features,
                key = { feature -> feature.title }
            ) { feature ->
                FeatureCard(
                    text = feature.title,
                    onClick = feature.onClick
                )
            }
        }
    }
}

private data class DebugFeature(
    val title: String,
    val onClick: () -> Unit
)

@Composable
private fun FeatureCard(
    text: String,
    onClick: () -> Unit
) {
    Card(onClick = onClick) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            text = text
        )
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun DebugToolsRootScreenPreview() = GrodnoRoadsM3ThemePreview {
    DebugToolsRootScreen(
        debugToolsRootComponent = DebugToolsRootComponentPreview(),
        onBack = {}
    )
}
