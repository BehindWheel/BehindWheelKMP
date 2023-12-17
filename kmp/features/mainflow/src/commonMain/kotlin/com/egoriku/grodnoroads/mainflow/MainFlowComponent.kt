package com.egoriku.grodnoroads.mainflow

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.settings.changelog.domain.component.ChangelogComponent
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("MainFlowComponent", exact = true)
interface MainFlowComponent {

    val childStack: Value<ChildStack<*, Child>>
    fun onBack()

    sealed class Child {
        data class Tabs(val component: TabsComponent) : Child()
        data object Appearance : Child()
        data class Changelog(val component: ChangelogComponent) : Child()
    }
}