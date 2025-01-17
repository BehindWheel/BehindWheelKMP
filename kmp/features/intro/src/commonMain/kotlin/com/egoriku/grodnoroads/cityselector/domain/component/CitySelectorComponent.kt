package com.egoriku.grodnoroads.cityselector.domain.component

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.cityselector.domain.store.CitySelectorStore.State
import com.egoriku.grodnoroads.shared.persistent.map.location.City
import kotlinx.coroutines.flow.StateFlow

@Stable
interface CitySelectorComponent {

    val state: StateFlow<State>

    fun modify(preference: CitySelectorPref)
    fun completeIntro()

    @Stable
    sealed interface CitySelectorPref {
        data class DefaultCity(
            val current: City = City.Grodno,
            val values: List<City> = City.entries
        ) : CitySelectorPref
    }
}
