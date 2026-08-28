package com.egoriku.grodnoroads.settings.changelog.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.egoriku.grodnoroads.foundation.navigation.backAnimation
import com.egoriku.grodnoroads.settings.changelog.domain.component.ChangelogFlowComponent
import com.egoriku.grodnoroads.settings.changelog.domain.component.ChangelogFlowComponent.Child

@Composable
fun ChangelogFlowScreen(
    changelogFlowComponent: ChangelogFlowComponent,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val stack by changelogFlowComponent.childStack.collectAsState()

    Children(
        modifier = modifier,
        stack = stack,
        animation = backAnimation(
            backHandler = changelogFlowComponent.backHandler,
            onBack = changelogFlowComponent::onBack
        )
    ) {
        when (val child = it.instance) {
            is Child.List -> ChangelogScreen(
                changelogComponent = child.component,
                onBack = onBack,
                onAddClick = changelogFlowComponent::onAddClick,
                onEditClick = changelogFlowComponent::onEditClick
            )
            is Child.AddEdit -> ChangelogAddScreen(
                changelogAddComponent = child.component
            )
        }
    }
}
