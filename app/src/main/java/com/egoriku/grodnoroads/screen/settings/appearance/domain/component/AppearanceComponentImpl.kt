package com.egoriku.grodnoroads.screen.settings.appearance.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearancePref
import com.egoriku.grodnoroads.screen.settings.appearance.domain.store.AppearanceStore
import com.egoriku.grodnoroads.screen.settings.appearance.domain.store.AppearanceStore.Intent
import com.egoriku.grodnoroads.screen.settings.appearance.domain.store.AppearanceStore.Intent.CloseDialog
import com.egoriku.grodnoroads.screen.settings.appearance.domain.store.AppearanceStore.State
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

internal class AppearanceComponentImpl(
    componentContext: ComponentContext
) : AppearanceComponent, ComponentContext by componentContext, KoinComponent {

    private val appearanceStore = instanceKeeper.getStore { get<AppearanceStore>() }

    override val state: Flow<State>
        get() = appearanceStore.states

    override fun modify(preference: AppearancePref) {
        appearanceStore.accept(Intent.Modify(preference))
    }

    override fun update(preference: AppearancePref) {
        appearanceStore.accept(Intent.Update(preference))
    }

    override fun closeDialog() {
        appearanceStore.accept(CloseDialog)
    }
}