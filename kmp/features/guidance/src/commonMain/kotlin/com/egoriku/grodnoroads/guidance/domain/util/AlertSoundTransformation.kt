package com.egoriku.grodnoroads.guidance.domain.util

import com.egoriku.grodnoroads.guidance.domain.model.Alert
import com.egoriku.grodnoroads.guidance.domain.model.Alert.CameraAlert
import com.egoriku.grodnoroads.guidance.domain.model.AppMode
import com.egoriku.grodnoroads.guidance.domain.model.CameraType.MediumSpeedCamera
import com.egoriku.grodnoroads.guidance.domain.model.CameraType.MobileCamera
import com.egoriku.grodnoroads.guidance.domain.model.CameraType.StationaryCamera
import com.egoriku.grodnoroads.guidance.domain.model.MapInternalConfig.AlertsInfo
import com.egoriku.grodnoroads.shared.models.MapEventType.CarCrash
import com.egoriku.grodnoroads.shared.models.MapEventType.RoadIncident
import com.egoriku.grodnoroads.shared.models.MapEventType.TrafficJam
import com.egoriku.grodnoroads.shared.models.MapEventType.TrafficPolice
import com.egoriku.grodnoroads.shared.models.MapEventType.WildAnimals

val alertPersistentList = emptyList<Alert>()

internal fun alertSoundTransformation(): suspend (List<Alert>, AlertsInfo, AppMode) -> List<Alert> {
    return { alerts, alertInfo, appMode ->
        if (alertInfo.voiceAlertsEnabled && appMode == AppMode.Drive) {
            alerts.mapNotNull { alert ->
                when (alert) {
                    is CameraAlert -> {
                        when (alert.cameraType) {
                            StationaryCamera -> when {
                                alertInfo.notifyStationaryCameras -> alert
                                else -> null
                            }
                            MobileCamera -> when {
                                alertInfo.notifyMobileCameras -> alert
                                else -> null
                            }
                            MediumSpeedCamera -> when {
                                alertInfo.notifyMediumSpeedCameras -> alert
                                else -> null
                            }
                        }
                    }
                    is Alert.IncidentAlert -> {
                        when (alert.mapEventType) {
                            TrafficPolice -> when {
                                alertInfo.notifyTrafficPolice -> alert
                                else -> null
                            }
                            RoadIncident -> when {
                                alertInfo.notifyRoadIncident -> alert
                                else -> null
                            }
                            CarCrash -> when {
                                alertInfo.notifyCarCrash -> alert
                                else -> null
                            }
                            TrafficJam -> when {
                                alertInfo.notifyTrafficJam -> alert
                                else -> null
                            }
                            WildAnimals -> when {
                                alertInfo.notifyWildAnimals -> alert
                                else -> null
                            }
                            else -> null
                        }
                    }
                }
            }
        } else {
            alertPersistentList
        }
    }
}
