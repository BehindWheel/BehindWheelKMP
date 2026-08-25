package com.egoriku.grodnoroads.settings.debugtools.domain.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.egoriku.grodnoroads.datastore.edit
import com.egoriku.grodnoroads.shared.persistent.debug.showMapDebugOverlay
import com.egoriku.grodnoroads.shared.persistent.debug.updateShowMapDebugOverlay
import com.egoriku.grodnoroads.shared.persistent.intro.showIntro
import com.egoriku.grodnoroads.shared.persistent.reporting.updateLastReportTime
import com.egoriku.grodnoroads.shared.persistent.reporting.updateReportsInLastHour
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun buildDataStoreEditComponent(
    componentContext: ComponentContext
): DataStoreEditComponent = DataStoreEditComponentImpl(componentContext)

internal class DataStoreEditComponentImpl(
    componentContext: ComponentContext
) : DataStoreEditComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val dataStore: DataStore<Preferences> by inject()
    private val componentScope = coroutineScope()

    override val showMapDebugOverlay: StateFlow<Boolean> = dataStore.data
        .map { it.showMapDebugOverlay }
        .stateIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    override fun setShowMapDebugOverlay(enabled: Boolean) {
        componentScope.launch {
            dataStore.edit {
                updateShowMapDebugOverlay(enabled)
            }
        }
    }

    override fun resetOnboarding() {
        componentScope.launch {
            dataStore.edit {
                showIntro(true)
            }
        }
    }

    override fun resetReportingLimit() {
        componentScope.launch {
            dataStore.edit {
                updateLastReportTime(0L)
                updateReportsInLastHour(0)
            }
        }
    }
}
