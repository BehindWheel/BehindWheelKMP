package com.egoriku.grodnoroads.eventreporting.ui.util

import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions
import com.egoriku.grodnoroads.localization.R

fun ReportingOptions.toStringResource() = when (this) {
    ReportingOptions.ROAD_REPAIR -> R.string.reporting_item_road_repair
    ReportingOptions.BROKEN_CAR -> R.string.reporting_item_broken_car
    ReportingOptions.TOW_TRUCK_WORKING -> R.string.reporting_item_tow_truck_working
    ReportingOptions.PEDESTRIAN_ON_ROAD -> R.string.reporting_item_pedestrian_on_road
    ReportingOptions.TRAFFIC_CONTROLLER -> R.string.reporting_item_traffic_controller
    ReportingOptions.NON_WORKING_TRAFFIC_LIGHTS -> R.string.reporting_item_non_working_traffic_lights
    ReportingOptions.OPEN_HATCH -> R.string.reporting_item_open_hatch
    ReportingOptions.ROAD_BLOCKED -> R.string.reporting_item_road_blocked
    ReportingOptions.OTHER_ROAD_INCIDENT -> R.string.reporting_item_other_road_incident
    ReportingOptions.TRAFFIC_JAM -> R.string.reporting_item_traffic_jam
    ReportingOptions.WILD_ANIMALS -> R.string.reporting_item_wild_animals
    ReportingOptions.CAR_ACCIDENT -> R.string.reporting_item_car_accident
    ReportingOptions.TRAFFIC_POLICE -> R.string.reporting_item_traffic_police
    ReportingOptions.WORKING_WITH_RADAR -> R.string.reporting_item_working_with_radar
    ReportingOptions.STAY_ON_BEACONS -> R.string.reporting_item_stay_on_beacons
    ReportingOptions.DOCUMENT_CHECK -> R.string.reporting_item_document_check
    ReportingOptions.STOPPED_ON_THE_MOVE -> R.string.reporting_item_stopped_on_the_move
    ReportingOptions.SITTING_IN_THE_CAR -> R.string.reporting_item_sitting_in_the_car
    ReportingOptions.WORKING_FROM_CAR -> R.string.reporting_item_working_from_car
    ReportingOptions.TRANSPORT_INSPECTION -> R.string.reporting_item_transport_inspection
    ReportingOptions.FILTER -> R.string.reporting_item_filter
}