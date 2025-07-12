package com.egoriku.grodnoroads.root.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.root.domain.RootStore.Intent
import com.egoriku.grodnoroads.root.domain.RootStore.Label
import com.egoriku.grodnoroads.root.domain.RootStore.Message
import com.egoriku.grodnoroads.root.domain.RootStore.RootState
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme.Auto
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme.Dark
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme.Light
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme.System
import com.egoriku.grodnoroads.shared.persistent.appearance.appTheme
import com.egoriku.grodnoroads.shared.persistent.map.location.City
import com.egoriku.grodnoroads.shared.persistent.map.location.defaultCity
import com.egoriku.grodnoroads.suntime.SunriseSunsetCalculator
import com.egoriku.grodnoroads.suntime.SunriseSunsetCalculator.SunTime
import com.egoriku.grodnoroads.suntime.SunriseSunsetCalculator.Twilight
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

sealed interface AppTheme {
    data object Dark : AppTheme
    data object Light : AppTheme
    data object System : AppTheme
}

internal interface RootStore : Store<Intent, RootState, Label> {

    data class RootState(
        val appTheme: AppTheme? = null,

        val city: City? = null,
        val theme: Theme? = null,
        val sunTime: SunTime? = null
    )

    sealed interface Label {
        data object UpdateThemeLabel : Label
    }

    sealed interface Intent {
        data object UpdateThemeIntent : Intent
    }

    sealed interface Message {
        data class UpdatePreferences(val theme: Theme, val city: City) : Message
        data class UpdateSunTime(val sunTime: SunTime) : Message
        data object ResetSunTime : Message
        data class UpdateAppTheme(val appTheme: AppTheme) : Message
    }
}

internal class RootStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {
    fun create(): RootStore = object :
        RootStore,
        Store<Intent, RootState, Label> by storeFactory.create(
            initialState = RootState(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                var calculateThemeJob: Job? = null

                onAction<Unit> {
                    combine(
                        dataStore.data.map { it.defaultCity }.distinctUntilChanged(),
                        dataStore.data.map { it.appTheme }.distinctUntilChanged()
                    ) { city, theme ->
                        dispatch(Message.UpdatePreferences(theme, city))

                        val appTheme = when (theme) {
                            System -> {
                                dispatch(Message.ResetSunTime)
                                AppTheme.System
                            }
                            Dark -> {
                                dispatch(Message.ResetSunTime)
                                AppTheme.Dark
                            }
                            Light -> {
                                dispatch(Message.ResetSunTime)
                                AppTheme.Light
                            }
                            Auto -> {
                                val currentDateTime = currentDateTime()

                                val sunTime = SunriseSunsetCalculator.calculate(
                                    date = currentDateTime,
                                    latitude = city.latLng.latitude,
                                    longitude = city.latLng.longitude,
                                    twilight = Twilight.Custom(2.0)
                                ) ?: return@combine AppTheme.System

                                dispatch(Message.UpdateSunTime(sunTime))

                                if (currentDateTime.time > sunTime.sunrise && currentDateTime.time < sunTime.sunset) {
                                    AppTheme.Light
                                } else {
                                    AppTheme.Dark
                                }
                            }
                        }
                        dispatch(Message.UpdateAppTheme(appTheme))
                        publish(Label.UpdateThemeLabel)
                    }.launchIn(this)
                }
                onIntent<Intent.UpdateThemeIntent> {
                    calculateThemeJob?.cancel()

                    val state = state()
                    val sunriseTime = state.sunTime?.sunrise
                    val sunsetTime = state.sunTime?.sunset

                    if (state.theme == Auto && sunriseTime != null && sunsetTime != null) {
                        calculateThemeJob = launch {
                            while (isActive) {
                                delay(1.minutes)

                                val currentDateTime = currentDateTime()
                                when {
                                    currentDateTime.time > sunriseTime && currentDateTime.time < sunsetTime -> {
                                        dispatch(Message.UpdateAppTheme(AppTheme.Light))
                                    }
                                    else -> {
                                        dispatch(Message.UpdateAppTheme(AppTheme.Dark))
                                    }
                                }
                            }
                        }
                    }
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.UpdatePreferences -> copy(theme = message.theme, city = message.city)
                    is Message.UpdateAppTheme -> copy(appTheme = message.appTheme)
                    is Message.UpdateSunTime -> copy(sunTime = message.sunTime)
                    is Message.ResetSunTime -> copy(sunTime = null)
                }
            }
        ) {}
}

@OptIn(ExperimentalTime::class)
private fun currentDateTime(): LocalDateTime {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}
