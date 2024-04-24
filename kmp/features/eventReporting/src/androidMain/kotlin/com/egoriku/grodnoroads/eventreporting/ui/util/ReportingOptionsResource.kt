package com.egoriku.grodnoroads.eventreporting.ui.util

import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions
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
import com.egoriku.grodnoroads.resources.MR

fun ReportingOptions.toStringResource() = when (this) {
    ROAD_REPAIR -> MR.strings.reporting_item_road_repair
    BROKEN_CAR -> MR.strings.reporting_item_broken_car
    TOW_TRUCK_WORKING -> MR.strings.reporting_item_tow_truck_working
    PEDESTRIAN_ON_ROAD -> MR.strings.reporting_item_pedestrian_on_road
    TRAFFIC_CONTROLLER -> MR.strings.reporting_item_traffic_controller
    NON_WORKING_TRAFFIC_LIGHTS -> MR.strings.reporting_item_non_working_traffic_lights
    OPEN_HATCH -> MR.strings.reporting_item_open_hatch
    ROAD_BLOCKED -> MR.strings.reporting_item_road_blocked
    OTHER_ROAD_INCIDENT -> MR.strings.reporting_item_other_road_incident
    TRAFFIC_JAM -> MR.strings.reporting_item_traffic_jam
    WILD_ANIMALS -> MR.strings.reporting_item_wild_animals
    CAR_ACCIDENT -> MR.strings.reporting_item_car_accident
    TRAFFIC_POLICE -> MR.strings.reporting_item_traffic_police
    WORKING_WITH_RADAR -> MR.strings.reporting_item_working_with_radar
    STAY_ON_BEACONS -> MR.strings.reporting_item_stay_on_beacons
    DOCUMENT_CHECK -> MR.strings.reporting_item_document_check
    STOPPED_ON_THE_MOVE -> MR.strings.reporting_item_stopped_on_the_move
    SITTING_IN_THE_CAR -> MR.strings.reporting_item_sitting_in_the_car
    WORKING_FROM_CAR -> MR.strings.reporting_item_working_from_car
    TRANSPORT_INSPECTION -> MR.strings.reporting_item_transport_inspection
    FILTER -> MR.strings.reporting_item_filter
}