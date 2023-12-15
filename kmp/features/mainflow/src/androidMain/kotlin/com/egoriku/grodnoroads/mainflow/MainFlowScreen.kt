package com.egoriku.grodnoroads.mainflow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.egoriku.grodnoroads.changelog.ui.ChangelogScreen
import com.egoriku.grodnoroads.mainflow.MainFlowComponent.Child
import com.egoriku.grodnoroads.mainflow.ui.TabsScreen

@Composable
fun MainFlowScreen(mainFlowComponent: MainFlowComponent) {
    Children(stack = mainFlowComponent.childStack) {
        when (val child = it.instance) {
            is Child.Tabs -> {
                TabsScreen(tabsComponent = child.component)
            }
            is Child.Appearance -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("appearance")
                }
            }
            is Child.Changelog -> ChangelogScreen(
                changelogComponent = child.component,
                onBack = mainFlowComponent::onBack
            )
        }
    }
}