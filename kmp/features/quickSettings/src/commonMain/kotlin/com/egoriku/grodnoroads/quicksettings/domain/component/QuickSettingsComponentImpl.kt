package com.egoriku.grodnoroads.quicksettings.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsStore
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsStore.Intent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun buildQuickSettingsComponent(
    componentContext: ComponentContext
): QuickSettingsComponent = QuickSettingsComponentImpl(componentContext)

internal class QuickSettingsComponentImpl(
    componentContext: ComponentContext
) : QuickSettingsComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val quickSettingsStore: QuickSettingsStore = instanceKeeper.getStore(::get)

    override val quickSettingsState = quickSettingsStore.stateFlow(lifecycle)

    override fun updatePreferences(pref: QuickSettingsPref) {
        quickSettingsStore.accept(Intent.Update(pref))
    }
}
