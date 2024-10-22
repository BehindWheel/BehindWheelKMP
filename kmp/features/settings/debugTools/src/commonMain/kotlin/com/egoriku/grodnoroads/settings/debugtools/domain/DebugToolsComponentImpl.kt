package com.egoriku.grodnoroads.settings.debugtools.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.decompose.ComponentContext
import com.egoriku.grodnoroads.datastore.edit
import com.egoriku.grodnoroads.extensions.decompose.coroutineScope
import com.egoriku.grodnoroads.shared.persistent.onboarding.showOnboarding
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun buildDebugToolsComponent(
    componentContext: ComponentContext
): DebugToolsComponent = DebugToolsComponentImpl(componentContext)

internal class DebugToolsComponentImpl(
    componentContext: ComponentContext
) : DebugToolsComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val dataStore: DataStore<Preferences> by inject()

    private val coroutineScope = coroutineScope()

    override fun showOnboarding() {
        coroutineScope.launch {
            dataStore.edit {
                showOnboarding(true)
            }
        }
    }
}
