package com.egoriku.grodnoroads.mainflow

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.egoriku.grodnoroads.settings.changelog.domain.component.ChangelogComponent
import com.egoriku.grodnoroads.settings.faq.domain.component.FaqComponent

interface MainFlowComponent: BackHandlerOwner {

    val childStack: Value<ChildStack<*, Child>>
    fun onBack()

    sealed class Child {
        data class Tabs(val component: TabsComponent) : Child()
        data object Appearance : Child()
        data class Changelog(val component: ChangelogComponent) : Child()
        data class Faq(val component: FaqComponent) : Child()
    }
}