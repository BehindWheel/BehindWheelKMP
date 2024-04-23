package com.egoriku.grodnoroads.eventreporting.domain

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.BROKEN_CAR
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.CAR_ACCIDENT
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.DOCUMENT_CHECK
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.FILTER
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.NON_WORKING_TRAFFIC_LIGHTS
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.OPEN_HATCH
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.OTHER_ROAD_INCIDENT
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.PEDESTRIAN_ON_ROAD
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.ROAD_BLOCKED
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.ROAD_REPAIR
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.SITTING_IN_THE_CAR
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.STAY_ON_BEACONS
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.STOPPED_ON_THE_MOVE
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.TOW_TRUCK_WORKING
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.TRAFFIC_CONTROLLER
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.TRAFFIC_JAM
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.TRAFFIC_POLICE
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.TRANSPORT_INSPECTION
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.WILD_ANIMALS
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.WORKING_FROM_CAR
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.WORKING_WITH_RADAR
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

object Reporting {
    @Stable
    sealed interface ReportType {
        val items: ImmutableList<ReportingOptions>

        data object RoadIncidents : ReportType {
            override val items: ImmutableList<ReportingOptions> =
                persistentListOf(
                    CAR_ACCIDENT,
                    ROAD_REPAIR,
                    ROAD_BLOCKED,
                    TRAFFIC_JAM,
                    OPEN_HATCH,
                    BROKEN_CAR,
                    OTHER_ROAD_INCIDENT
                )
        }

        data object TrafficPolice : ReportType {
            override val items: ImmutableList<ReportingOptions> =
                persistentListOf(
                    TRAFFIC_POLICE,
                    WORKING_WITH_RADAR,
                    STAY_ON_BEACONS,
                    DOCUMENT_CHECK,
                    STOPPED_ON_THE_MOVE,
                    SITTING_IN_THE_CAR,
                    WORKING_FROM_CAR,
                    TRANSPORT_INSPECTION,
                    TRAFFIC_CONTROLLER,
                    FILTER
                )
        }

        data object Other : ReportType {
            override val items: ImmutableList<ReportingOptions> =
                persistentListOf(
                    TOW_TRUCK_WORKING,
                    NON_WORKING_TRAFFIC_LIGHTS,
                    WILD_ANIMALS,
                    PEDESTRIAN_ON_ROAD
                )
        }

        data object MobileCamera : ReportType {
            override val items: ImmutableList<ReportingOptions> = persistentListOf()
        }
    }
}