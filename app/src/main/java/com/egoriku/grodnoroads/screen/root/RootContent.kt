package com.egoriku.grodnoroads.screen.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.egoriku.grodnoroads.screen.main.MainUi
import com.egoriku.grodnoroads.screen.root.RoadsRootComponent.Child.Main
import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampType
import com.egoriku.grodnoroads.screen.root.ui.HeadLampDialog

@Composable
fun RootContent(roadsRootComponent: RoadsRootComponent) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val dialogState by roadsRootComponent.headlampDialogState.collectAsState(initial = HeadLampType.None)

        if (dialogState != HeadLampType.None) {
            HeadLampDialog(
                headlampType = dialogState,
                onClose = roadsRootComponent::closeHeadlampDialog
            )
        }

        Children(stack = roadsRootComponent.childStack) {
            when (val child = it.instance) {
                is Main -> MainUi(component = child.component)
            }
        }
    }
}