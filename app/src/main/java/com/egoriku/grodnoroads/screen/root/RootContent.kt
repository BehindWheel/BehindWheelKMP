package com.egoriku.grodnoroads.screen.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.egoriku.grodnoroads.screen.main.MainUi
import com.egoriku.grodnoroads.screen.root.RoadsRootComponent.Child.Main

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootContent(roadsRootComponent: RoadsRootComponent) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Children(
            stack = roadsRootComponent.childStack,
            animation = stackAnimation(fade())
        ) {
            when (val child = it.instance) {
                is Main -> MainUi(component = child.component)
            }
        }
    }
}