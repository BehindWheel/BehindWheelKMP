package com.egoriku.grodnoroads.settings.alerts.domain.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.datastore.edit
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertSettings
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertSettings.AlertEvents
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertSettings.AlertRadius
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertSettings.VolumeInfo
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.AlertAvailability
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.AlertRadiusInCity
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.AlertRadiusOutCity
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.AlertVolumeLevel
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.CarCrash
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.MediumSpeedCameras
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.MobileCameras
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.RoadIncident
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.SelectAll
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.StationaryCameras
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.TrafficJam
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.TrafficPolice
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.WildAnimals
import com.egoriku.grodnoroads.settings.alerts.domain.store.AlertsStore.AlertsIntent
import com.egoriku.grodnoroads.settings.alerts.domain.store.AlertsStore.Message
import com.egoriku.grodnoroads.settings.alerts.domain.store.AlertsStore.StoreAlertState
import com.egoriku.grodnoroads.shared.persistent.Selectable
import com.egoriku.grodnoroads.shared.persistent.alert.DEFAULT_ALERT_DISTANCE_IN_CITY
import com.egoriku.grodnoroads.shared.persistent.alert.DEFAULT_ALERT_DISTANCE_OUTSIDE_CITY
import com.egoriku.grodnoroads.shared.persistent.alert.alertsDistanceInCity
import com.egoriku.grodnoroads.shared.persistent.alert.alertsDistanceOutsideCity
import com.egoriku.grodnoroads.shared.persistent.alert.alertsEnabled
import com.egoriku.grodnoroads.shared.persistent.alert.alertsVoiceAlertEnabled
import com.egoriku.grodnoroads.shared.persistent.alert.alertsVolumeLevel
import com.egoriku.grodnoroads.shared.persistent.alert.isNotifyCarCrash
import com.egoriku.grodnoroads.shared.persistent.alert.isNotifyMediumSpeedCameras
import com.egoriku.grodnoroads.shared.persistent.alert.isNotifyMobileCameras
import com.egoriku.grodnoroads.shared.persistent.alert.isNotifyRoadIncidents
import com.egoriku.grodnoroads.shared.persistent.alert.isNotifyStationaryCameras
import com.egoriku.grodnoroads.shared.persistent.alert.isNotifyTrafficJam
import com.egoriku.grodnoroads.shared.persistent.alert.isNotifyTrafficPolice
import com.egoriku.grodnoroads.shared.persistent.alert.isNotifyWildAnimals
import com.egoriku.grodnoroads.shared.persistent.alert.updateAlertsAvailability
import com.egoriku.grodnoroads.shared.persistent.alert.updateAlertsDistanceInCity
import com.egoriku.grodnoroads.shared.persistent.alert.updateAlertsDistanceOutsideCity
import com.egoriku.grodnoroads.shared.persistent.alert.updateAlertsVoiceAlertAvailability
import com.egoriku.grodnoroads.shared.persistent.alert.updateAlertsVolumeInfo
import com.egoriku.grodnoroads.shared.persistent.alert.updateNotifyCarCrash
import com.egoriku.grodnoroads.shared.persistent.alert.updateNotifyMediumSpeedCameras
import com.egoriku.grodnoroads.shared.persistent.alert.updateNotifyMobileCameras
import com.egoriku.grodnoroads.shared.persistent.alert.updateNotifyRoadIncidents
import com.egoriku.grodnoroads.shared.persistent.alert.updateNotifyStationaryCameras
import com.egoriku.grodnoroads.shared.persistent.alert.updateNotifyTrafficJam
import com.egoriku.grodnoroads.shared.persistent.alert.updateNotifyTrafficPolice
import com.egoriku.grodnoroads.shared.persistent.alert.updateNotifyWildAnimals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class AlertsStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {
    fun create(): AlertsStore = object : AlertsStore,
        Store<AlertsIntent, StoreAlertState, Nothing> by storeFactory.create(
            initialState = StoreAlertState(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    dataStore.data
                        .map { pref ->
                            val notifyStationaryCameras = pref.isNotifyStationaryCameras
                            val notifyMediumSpeedCameras = pref.isNotifyMediumSpeedCameras
                            val notifyMobileCameras = pref.isNotifyMobileCameras
                            val notifyTrafficPolice = pref.isNotifyTrafficPolice
                            val notifyRoadIncidents = pref.isNotifyRoadIncidents
                            val notifyCarCrash = pref.isNotifyCarCrash
                            val notifyTrafficJam = pref.isNotifyTrafficJam
                            val notifyWildAnimals = pref.isNotifyWildAnimals

                            val isShowMarkers = listOf(
                                notifyStationaryCameras, notifyMediumSpeedCameras,
                                notifyMobileCameras, notifyTrafficPolice,
                                notifyRoadIncidents, notifyCarCrash,
                                notifyTrafficJam, notifyWildAnimals
                            )

                            val isAllMarkersDisabled = isShowMarkers.none { it }
                            val isAllMarkersEnabled = isShowMarkers.all { it }
                            val selectable = when {
                                isAllMarkersDisabled -> Selectable.AllDisabled
                                isAllMarkersEnabled -> Selectable.AllEnabled
                                else -> Selectable.Mixed
                            }

                            AlertSettings(
                                alertAvailability = AlertAvailability(
                                    alertFeatureEnabled = pref.alertsEnabled,
                                    voiceAlertEnabled = pref.alertsVoiceAlertEnabled
                                ),
                                volumeInfo = VolumeInfo(
                                    alertVolumeLevel = AlertVolumeLevel(
                                        current = pref.alertsVolumeLevel
                                    )
                                ),
                                alertRadius = AlertRadius(
                                    alertRadiusInCity = AlertRadiusInCity(
                                        current = pref.alertsDistanceInCity
                                    ),
                                    alertRadiusOutCity = AlertRadiusOutCity(
                                        current = pref.alertsDistanceOutsideCity
                                    )
                                ),
                                alertEvents = AlertEvents(
                                    stationaryCameras = StationaryCameras(isNotify = notifyStationaryCameras),
                                    mediumSpeedCameras = MediumSpeedCameras(isNotify = notifyMediumSpeedCameras),
                                    mobileCameras = MobileCameras(isNotify = notifyMobileCameras),
                                    trafficPolice = TrafficPolice(isNotify = notifyTrafficPolice),
                                    roadIncident = RoadIncident(isNotify = notifyRoadIncidents),
                                    carCrash = CarCrash(isNotify = notifyCarCrash),
                                    trafficJam = TrafficJam(isNotify = notifyTrafficJam),
                                    wildAnimals = WildAnimals(isNotify = notifyWildAnimals),
                                    selectable = selectable
                                )
                            )
                        }
                        .distinctUntilChanged()
                        .onEach {
                            dispatch(Message.NewSettings(it))
                            dispatch(Message.Loading(false))
                        }
                        .launchIn(this)
                }
                onIntent<AlertsIntent.Modify> {
                    launch {
                        dataStore.edit {
                            when (val pref = it.pref) {
                                is AlertAvailability -> {
                                    if (pref.alertFeatureEnabled) {
                                        updateAlertsAvailability(pref.alertFeatureEnabled)
                                        updateAlertsVoiceAlertAvailability(pref.voiceAlertEnabled)
                                    } else {
                                        updateAlertsAvailability(false)
                                        updateAlertsVoiceAlertAvailability(false)
                                    }
                                }

                                is AlertVolumeLevel -> updateAlertsVolumeInfo(pref.current.levelName)

                                is AlertRadiusInCity -> updateAlertsDistanceInCity(pref.current)
                                is AlertRadiusOutCity -> updateAlertsDistanceOutsideCity(pref.current)

                                is CarCrash -> updateNotifyCarCrash(pref.isNotify)
                                is MediumSpeedCameras -> updateNotifyMediumSpeedCameras(pref.isNotify)
                                is MobileCameras -> updateNotifyMobileCameras(pref.isNotify)
                                is RoadIncident -> updateNotifyRoadIncidents(pref.isNotify)
                                is StationaryCameras -> updateNotifyStationaryCameras(pref.isNotify)
                                is TrafficJam -> updateNotifyTrafficJam(pref.isNotify)
                                is TrafficPolice -> updateNotifyTrafficPolice(pref.isNotify)
                                is WildAnimals -> updateNotifyWildAnimals(pref.isNotify)
                                is SelectAll -> {
                                    updateNotifyCarCrash(pref.selectAll)
                                    updateNotifyMediumSpeedCameras(pref.selectAll)
                                    updateNotifyMobileCameras(pref.selectAll)
                                    updateNotifyRoadIncidents(pref.selectAll)
                                    updateNotifyStationaryCameras(pref.selectAll)
                                    updateNotifyTrafficJam(pref.selectAll)
                                    updateNotifyTrafficPolice(pref.selectAll)
                                    updateNotifyWildAnimals(pref.selectAll)
                                }
                            }
                        }
                    }
                }
                onIntent<AlertsIntent.Reset> {
                    launch {
                        dataStore.edit {
                            when (it.pref) {
                                is AlertRadiusInCity -> updateAlertsDistanceInCity(
                                    DEFAULT_ALERT_DISTANCE_IN_CITY
                                )

                                is AlertRadiusOutCity -> updateAlertsDistanceOutsideCity(
                                    DEFAULT_ALERT_DISTANCE_OUTSIDE_CITY
                                )

                                else -> Unit
                            }
                        }
                    }
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.NewSettings -> copy(alertSettings = message.alertSettingsState)
                    is Message.Loading -> copy(isLoading = message.isLoading)
                }
            }
        ) {}
}