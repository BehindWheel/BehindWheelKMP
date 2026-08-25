package com.egoriku.grodnoroads.settings.debugtools.domain

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.egoriku.grodnoroads.settings.debugtools.domain.auth.AuthComponent
import com.egoriku.grodnoroads.settings.debugtools.domain.datastore.DataStoreEditComponent
import com.egoriku.grodnoroads.settings.debugtools.domain.root.DebugToolsRootComponent
import kotlinx.coroutines.flow.StateFlow

@Stable
interface DebugToolsComponent : BackHandlerOwner {

    val childStack: StateFlow<ChildStack<*, Child>>

    fun onBack()

    sealed interface Child {
        data class Root(val component: DebugToolsRootComponent) : Child
        data object UIKit : Child
        data class DataStoreEdit(val component: DataStoreEditComponent) : Child
        data object Palette : Child
        data class Auth(val component: AuthComponent) : Child
        data object SpecialEvents : Child
    }
}
