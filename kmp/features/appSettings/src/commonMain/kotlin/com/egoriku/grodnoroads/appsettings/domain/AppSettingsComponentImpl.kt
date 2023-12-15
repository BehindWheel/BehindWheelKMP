package com.egoriku.grodnoroads.appsettings.domain

import com.arkivanov.decompose.ComponentContext
import com.egoriku.grodnoroads.shared.components.AppBuildConfig
import com.egoriku.grodnoroads.shared.models.Page
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun buildAppSettingsComponent(
    componentContext: ComponentContext,
    onOpen: (Page) -> Unit
): AppSettingsComponent = AppSettingsComponentImpl(
    componentContext = componentContext,
    onOpen = onOpen
)

internal class AppSettingsComponentImpl(
    componentContext: ComponentContext,
    private val onOpen: (Page) -> Unit
) : AppSettingsComponent, KoinComponent, ComponentContext by componentContext {

    private val appBuildConfig by inject<AppBuildConfig>()

    override val appVersion = appBuildConfig.versionName

    override fun open(page: Page) = onOpen(page)
}