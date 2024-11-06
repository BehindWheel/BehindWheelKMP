package com.egoriku.grodnoroads.cityselector.domain.store

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.cityselector.domain.component.CitySelectorComponent.CitySelectorPref
import com.egoriku.grodnoroads.cityselector.domain.component.CitySelectorComponent.CitySelectorPref.DefaultCity
import com.egoriku.grodnoroads.cityselector.domain.store.CitySelectorStore.Intent
import com.egoriku.grodnoroads.cityselector.domain.store.CitySelectorStore.Label
import com.egoriku.grodnoroads.cityselector.domain.store.CitySelectorStore.State
import com.egoriku.grodnoroads.shared.persistent.map.location.City

interface CitySelectorStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object CompleteIntro : Intent
        data class Modify(val preference: CitySelectorPref) : Intent
    }

    data class State(val defaultCity: DefaultCity = DefaultCity())

    sealed interface Label {
        data object FinishIntro : Label
    }

    sealed interface Message {
        data class OnUpdateCity(val city: City) : Message
    }
}
