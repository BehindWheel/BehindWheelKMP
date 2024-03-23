package com.egoriku.grodnoroads.eventreporting.domain

import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.*
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

object Reporting {
    data class ReportType(val items: ImmutableList<ReportingOptions>)

    val roadIncidents = ReportType(
        items = persistentListOf(
            CAR_ACCIDENT,
            ROAD_REPAIR,
            ROAD_BLOCKED,
            TRAFFIC_JAM,
            OPEN_HATCH,
            BROKEN_CAR,
            OTHER_ROAD_INCIDENT
        )
    )
    val trafficPolice = ReportType(
        items = persistentListOf(
            TRAFFIC_POLICE,
            WORKING_WITH_RADAR,
            STAY_ON_BEACONS,
            DOCUMENT_CHECK,
            STOPPED_ON_THE_MOVE,
            SITTING_IN_THE_CAR,
            WORKING_FROM_CAR,
            TRANSPORT_INSPECTION,
            TRAFFIC_CONTROLLER,
            FILTER,
            OTHER_TRAFFIC_POLICE,
        )
    )
    val other = ReportType(
        items = persistentListOf(
            TOW_TRUCK_WORKING,
            NON_WORKING_TRAFFIC_LIGHTS,
            WILD_ANIMALS,
            PEDESTRIAN_ON_ROAD
        )
    )
}