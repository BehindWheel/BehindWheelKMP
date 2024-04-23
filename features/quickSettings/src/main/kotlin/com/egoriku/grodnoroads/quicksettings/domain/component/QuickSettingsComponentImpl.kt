package com.egoriku.grodnoroads.quicksettings.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.quicksettings.domain.model.QuickSettingsState
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsStore
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsStore.Intent
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun buildQuickSettingsComponent(
    componentContext: ComponentContext
): QuickSettingsComponent = QuickSettingsComponentImpl(componentContext)

internal class QuickSettingsComponentImpl(
    componentContext: ComponentContext
) : QuickSettingsComponent, ComponentContext by componentContext, KoinComponent {

    private val quickSettingsStore: QuickSettingsStore = instanceKeeper.getStore(::get)

    override val quickSettingsState: Flow<QuickSettingsState>
        get() = quickSettingsStore.states

    override fun updatePreferences(pref: QuickSettingsPref) {
        quickSettingsStore.accept(Intent.Update(pref))
    }
}