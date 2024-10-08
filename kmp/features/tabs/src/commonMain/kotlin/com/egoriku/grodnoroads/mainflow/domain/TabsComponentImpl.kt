package com.egoriku.grodnoroads.mainflow.domain

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.egoriku.grodnoroads.appsettings.domain.component.buildAppSettingsComponent
import com.egoriku.grodnoroads.coroutines.flow.CStateFlow
import com.egoriku.grodnoroads.coroutines.flow.toCStateFlow
import com.egoriku.grodnoroads.coroutines.toStateFlow
import com.egoriku.grodnoroads.guidance.domain.component.buildGuidanceComponent
import com.egoriku.grodnoroads.mainflow.domain.TabsComponent.Child
import com.egoriku.grodnoroads.shared.models.Page
import com.egoriku.grodnoroads.shared.models.reporting.ReportParams
import kotlinx.serialization.Serializable

fun buildTabComponent(
    componentContext: ComponentContext,
    onOpenPage: (Page) -> Unit
): TabsComponent = TabsComponentImpl(
    componentContext = componentContext,
    onOpenPage = onOpenPage
)

internal class TabsComponentImpl(
    componentContext: ComponentContext,
    private val onOpenPage: (Page) -> Unit
) : TabsComponent,
    ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    private val reportingNavigation = SlotNavigation<ReportingConfig>()

    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Guidance,
        handleBackButton = true,
        key = "TabStack",
        childFactory = ::processChild
    )

    override val childStack: CStateFlow<ChildStack<*, Child>> = stack.toStateFlow().toCStateFlow()
    override val childSlot: CStateFlow<ChildSlot<*, Any>> =
        childSlot(
            source = reportingNavigation,
            serializer = ReportingConfig.serializer()
        ) { _, _ ->
            Any()
        }.toStateFlow().toCStateFlow()

    private fun processChild(
        config: Config,
        componentContext: ComponentContext
    ) = when (config) {
        is Config.Guidance -> Child.Guidance(
            buildGuidanceComponent(
                componentContext = componentContext,
                onOpenReporting = {
                    reportingNavigation.activate(ReportingConfig)
                }
            )
        )
        is Config.AppSettings -> Child.AppSettings(
            buildAppSettingsComponent(
                componentContext = componentContext,
                onOpen = onOpenPage
            )
        )
    }

    override fun onSelectTab(index: Int) {
        if (index == 0) {
            navigation.replaceAll(Config.Guidance)
        } else {
            navigation.bringToFront(Config.AppSettings)
        }
    }

    override fun processReporting(params: ReportParams) {
        (stack.active.instance as? Child.Guidance)?.component?.processReporting(params)
    }

    override fun closeReporting() = reportingNavigation.dismiss()

    @Serializable
    object ReportingConfig

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Guidance : Config

        @Serializable
        data object AppSettings : Config
    }
}
