package com.egoriku.grodnoroads.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.egoriku.grodnoroads.tabs.MainFlowComponent.Child
import com.egoriku.grodnoroads.tabs.ui.TabsScreen

@Composable
fun MainFlowScreen(mainFlowComponent: MainFlowComponent) {
    Children(stack = mainFlowComponent.childStack) {
        when (val child = it.instance) {
            is Child.Tabs -> {
                TabsScreen(tabsComponent = child.tabsComponent)
            }
            is Child.Appearance -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("appearance")
                }
            }
        }
    }
}