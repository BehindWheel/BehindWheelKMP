package com.egoriku.grodnoroads.settings.debugtools

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.egoriku.grodnoroads.foundation.navigation.backAnimation
import com.egoriku.grodnoroads.settings.debugtools.domain.DebugToolsComponent
import com.egoriku.grodnoroads.settings.debugtools.domain.DebugToolsComponent.Child
import com.egoriku.grodnoroads.settings.debugtools.screen.auth.AuthScreen
import com.egoriku.grodnoroads.settings.debugtools.screen.datastore.DataStoreEditScreen
import com.egoriku.grodnoroads.settings.debugtools.screen.palette.PaletteScreen
import com.egoriku.grodnoroads.settings.debugtools.screen.root.DebugToolsRootScreen
import com.egoriku.grodnoroads.settings.debugtools.screen.specialevent.SpecialEventScreen
import com.egoriku.grodnoroads.settings.debugtools.screen.uikit.UIKitScreen

@Composable
fun DebugToolsScreen(
    debugToolsComponent: DebugToolsComponent,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val stack by debugToolsComponent.childStack.collectAsState()

    Children(
        modifier = modifier,
        stack = stack,
        animation = backAnimation(
            backHandler = debugToolsComponent.backHandler,
            onBack = debugToolsComponent::onBack
        )
    ) {
        when (val child = it.instance) {
            is Child.Root -> DebugToolsRootScreen(
                debugToolsRootComponent = child.component,
                onBack = onBack
            )
            is Child.UIKit -> UIKitScreen(
                onBack = debugToolsComponent::onBack
            )
            is Child.DataStoreEdit -> DataStoreEditScreen(
                dataStoreEditComponent = child.component,
                onBack = debugToolsComponent::onBack
            )
            is Child.Palette -> PaletteScreen(
                onBack = debugToolsComponent::onBack
            )
            is Child.Auth -> AuthScreen(
                authComponent = child.component,
                onBack = debugToolsComponent::onBack
            )
            is Child.SpecialEvents -> SpecialEventScreen(
                onBack = debugToolsComponent::onBack
            )
        }
    }
}
