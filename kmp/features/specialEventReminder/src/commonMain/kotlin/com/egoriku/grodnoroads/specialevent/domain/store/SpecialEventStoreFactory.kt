package com.egoriku.grodnoroads.specialevent.domain.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.datastore.edit
import com.egoriku.grodnoroads.extensions.DateTime.currentDateTime
import com.egoriku.grodnoroads.shared.persistent.map.location.defaultCity
import com.egoriku.grodnoroads.shared.persistent.specialevent.specialEventDismissedDate
import com.egoriku.grodnoroads.shared.persistent.specialevent.updateSpecialEventDismissedDate
import com.egoriku.grodnoroads.specialevent.domain.model.EventType
import com.egoriku.grodnoroads.specialevent.domain.store.SpecialEventStore.Intent
import com.egoriku.grodnoroads.specialevent.domain.store.SpecialEventStore.State
import com.egoriku.grodnoroads.specialevent.domain.store.SpecialEventStoreFactory.Message.EventTypeChanged
import com.egoriku.grodnoroads.specialevent.domain.util.SpecialEventDispatcher
import com.egoriku.grodnoroads.suntime.SunriseSunsetCalculator
import com.egoriku.grodnoroads.suntime.SunriseSunsetCalculator.Twilight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

class SpecialEventStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {

    private sealed interface Message {
        data class EventTypeChanged(val eventType: EventType) : Message
    }

    fun create(): SpecialEventStore = object :
        SpecialEventStore,
        Store<Intent, State, Nothing> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    launch {
                        val eventType = SpecialEventDispatcher.calculateType()
                        if (eventType != null && isEligibleToShow()) {
                            dispatch(EventTypeChanged(eventType))
                        }
                    }
                }
                onIntent<Intent.DismissToday> {
                    launch {
                        dataStore.edit {
                            updateSpecialEventDismissedDate(currentDateTime().date.toString())
                        }
                    }
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is EventTypeChanged -> copy(eventType = message.eventType)
                }
            }
        ) {}

    private suspend fun isEligibleToShow(): Boolean {
        val preferences = dataStore.data.first()
        val currentDateTime = currentDateTime()
        val city = preferences.defaultCity
        val sunTime = SunriseSunsetCalculator.calculate(
            date = currentDateTime,
            latitude = city.latLng.latitude,
            longitude = city.latLng.longitude,
            twilight = Twilight.Official
        )

        return isEligibleToShow(
            currentDateTime = currentDateTime,
            dismissedDate = preferences.specialEventDismissedDate,
            sunTime = sunTime
        )
    }
}

internal fun isEligibleToShow(
    currentDateTime: LocalDateTime,
    dismissedDate: String?,
    sunTime: SunriseSunsetCalculator.SunTime?
): Boolean {
    if (dismissedDate == currentDateTime.date.toString()) {
        return false
    }

    if (sunTime == null) {
        return true
    }

    return currentDateTime.time > sunTime.sunrise && currentDateTime.time < sunTime.sunset
}
