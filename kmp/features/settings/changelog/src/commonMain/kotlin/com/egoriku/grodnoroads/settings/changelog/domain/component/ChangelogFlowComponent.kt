package com.egoriku.grodnoroads.settings.changelog.domain.component

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogEntry
import kotlinx.coroutines.flow.StateFlow

@Stable
interface ChangelogFlowComponent : BackHandlerOwner {

    val childStack: StateFlow<ChildStack<*, Child>>

    fun onBack()
    fun onAddClick()
    fun onEditClick(entry: ChangelogEntry)

    sealed interface Child {
        data class List(val component: ChangelogComponent) : Child
        data class AddEdit(val component: ChangelogAddComponent) : Child
    }
}
