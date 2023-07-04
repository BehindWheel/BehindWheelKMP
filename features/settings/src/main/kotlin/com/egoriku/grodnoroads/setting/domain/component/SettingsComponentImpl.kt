package com.egoriku.grodnoroads.setting.domain.component

import com.arkivanov.decompose.ComponentContext
import com.egoriku.grodnoroads.shared.appcomponent.AppBuildConfig
import com.egoriku.grodnoroads.shared.appcomponent.Page
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun buildSettingsComponent(
    componentContext: ComponentContext,
    onOpen: (Page) -> Unit
): SettingsComponent = SettingsComponentImpl(
    componentContext = componentContext,
    onOpen = onOpen
)

internal class SettingsComponentImpl(
    componentContext: ComponentContext,
    private val onOpen: (Page) -> Unit
) : SettingsComponent, KoinComponent, ComponentContext by componentContext {

    private val appBuildConfig by inject<AppBuildConfig>()

    override val appVersion = appBuildConfig.versionName

    override fun open(page: Page) = onOpen(page)
}