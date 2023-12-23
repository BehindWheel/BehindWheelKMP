package com.egoriku.grodnoroads.screen.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.FaultyDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.*
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.predictiveback.predictiveBackAnimation
import com.egoriku.grodnoroads.mainflow.screen.TabsScreen
import com.egoriku.grodnoroads.screen.root.RoadsRootComponent.Child
import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampType
import com.egoriku.grodnoroads.screen.root.ui.HeadLampDialog

@OptIn(FaultyDecomposeApi::class, ExperimentalDecomposeApi::class)
@Composable
fun RootContent(rootComponent: RoadsRootComponent) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val dialogState by rootComponent.headlampDialogState.collectAsState(initial = HeadLampType.None)

        if (dialogState != HeadLampType.None) {
            HeadLampDialog(
                headlampType = dialogState,
                onClose = rootComponent::closeHeadlampDialog
            )
        }

        Children(
            stack = rootComponent.childStack,
            animation = predictiveBackAnimation(
                backHandler = rootComponent.backHandler,
                animation = stackAnimation { _, _, direction ->
                    if (direction.isFront) {
                        slide() + fade()
                    } else {
                        scale(frontFactor = 1F, backFactor = 0.7F) + fade()
                    }
                },
                onBack = rootComponent::onBack,
            ),
        ) {
            when (val child = it.instance) {
                is Child.Main -> TabsScreen(tabsComponent = child.component)
            }
        }
    }
}