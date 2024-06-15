package com.egoriku.grodnoroads.eventreporting.ui.util

import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.reporting_item_broken_car
import com.egoriku.grodnoroads.compose.resources.reporting_item_car_accident
import com.egoriku.grodnoroads.compose.resources.reporting_item_document_check
import com.egoriku.grodnoroads.compose.resources.reporting_item_filter
import com.egoriku.grodnoroads.compose.resources.reporting_item_non_working_traffic_lights
import com.egoriku.grodnoroads.compose.resources.reporting_item_open_hatch
import com.egoriku.grodnoroads.compose.resources.reporting_item_other_road_incident
import com.egoriku.grodnoroads.compose.resources.reporting_item_pedestrian_on_road
import com.egoriku.grodnoroads.compose.resources.reporting_item_road_blocked
import com.egoriku.grodnoroads.compose.resources.reporting_item_road_repair
import com.egoriku.grodnoroads.compose.resources.reporting_item_sitting_in_the_car
import com.egoriku.grodnoroads.compose.resources.reporting_item_stay_on_beacons
import com.egoriku.grodnoroads.compose.resources.reporting_item_stopped_on_the_move
import com.egoriku.grodnoroads.compose.resources.reporting_item_tow_truck_working
import com.egoriku.grodnoroads.compose.resources.reporting_item_traffic_controller
import com.egoriku.grodnoroads.compose.resources.reporting_item_traffic_jam
import com.egoriku.grodnoroads.compose.resources.reporting_item_traffic_police
import com.egoriku.grodnoroads.compose.resources.reporting_item_transport_inspection
import com.egoriku.grodnoroads.compose.resources.reporting_item_wild_animals
import com.egoriku.grodnoroads.compose.resources.reporting_item_working_from_car
import com.egoriku.grodnoroads.compose.resources.reporting_item_working_with_radar
import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions

fun ReportingOptions.toStringResource() = when (this) {
    ReportingOptions.ROAD_REPAIR -> Res.string.reporting_item_road_repair
    ReportingOptions.BROKEN_CAR -> Res.string.reporting_item_broken_car
    ReportingOptions.TOW_TRUCK_WORKING -> Res.string.reporting_item_tow_truck_working
    ReportingOptions.PEDESTRIAN_ON_ROAD -> Res.string.reporting_item_pedestrian_on_road
    ReportingOptions.TRAFFIC_CONTROLLER -> Res.string.reporting_item_traffic_controller
    ReportingOptions.NON_WORKING_TRAFFIC_LIGHTS -> Res.string.reporting_item_non_working_traffic_lights
    ReportingOptions.OPEN_HATCH -> Res.string.reporting_item_open_hatch
    ReportingOptions.ROAD_BLOCKED -> Res.string.reporting_item_road_blocked
    ReportingOptions.OTHER_ROAD_INCIDENT -> Res.string.reporting_item_other_road_incident
    ReportingOptions.TRAFFIC_JAM -> Res.string.reporting_item_traffic_jam
    ReportingOptions.WILD_ANIMALS -> Res.string.reporting_item_wild_animals
    ReportingOptions.CAR_ACCIDENT -> Res.string.reporting_item_car_accident
    ReportingOptions.TRAFFIC_POLICE -> Res.string.reporting_item_traffic_police
    ReportingOptions.WORKING_WITH_RADAR -> Res.string.reporting_item_working_with_radar
    ReportingOptions.STAY_ON_BEACONS -> Res.string.reporting_item_stay_on_beacons
    ReportingOptions.DOCUMENT_CHECK -> Res.string.reporting_item_document_check
    ReportingOptions.STOPPED_ON_THE_MOVE -> Res.string.reporting_item_stopped_on_the_move
    ReportingOptions.SITTING_IN_THE_CAR -> Res.string.reporting_item_sitting_in_the_car
    ReportingOptions.WORKING_FROM_CAR -> Res.string.reporting_item_working_from_car
    ReportingOptions.TRANSPORT_INSPECTION -> Res.string.reporting_item_transport_inspection
    ReportingOptions.FILTER -> Res.string.reporting_item_filter
}