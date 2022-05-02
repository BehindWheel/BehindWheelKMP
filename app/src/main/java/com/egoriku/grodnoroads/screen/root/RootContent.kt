package com.egoriku.grodnoroads.screen.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.childAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.fade
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.plus
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.scale
import com.egoriku.grodnoroads.screen.main.MainUi
import com.egoriku.grodnoroads.screen.root.RoadsRootComponent.Child

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootContent(roadsRootComponent: RoadsRootComponent) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Children(
            routerState = roadsRootComponent.routerState,
            animation = childAnimation(scale() + fade())
        ) {
            when (val child = it.instance) {
                is Child.Main -> MainUi(component = child.component)
            }
        }
    }
}