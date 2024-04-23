package com.egoriku.grodnoroads.eventreporting.domain

import com.egoriku.grodnoroads.eventreporting.domain.ReportingOptions.*
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.shared.core.models.MapEventType

enum class ReportingOptions(
    val toSend: String,
    val mapEventType: MapEventType
) {
    ROAD_REPAIR(toSend = "Дорожные работы", mapEventType = MapEventType.RoadIncident),
    BROKEN_CAR(toSend = "Сломалась машина", mapEventType = MapEventType.RoadIncident),
    TOW_TRUCK_WORKING(toSend = "Работает эвакуатор", mapEventType = MapEventType.RoadIncident),
    PEDESTRIAN_ON_ROAD(toSend = "Пешеход на дороге", mapEventType = MapEventType.RoadIncident),
    TRAFFIC_CONTROLLER("Регулировщик", mapEventType = MapEventType.RoadIncident),
    NON_WORKING_TRAFFIC_LIGHTS(toSend = "Не работают светофоры", mapEventType = MapEventType.RoadIncident),
    OPEN_HATCH(toSend = "Открытый люк", mapEventType = MapEventType.RoadIncident),
    ROAD_BLOCKED(toSend = "Дорога перекрыта", mapEventType = MapEventType.RoadIncident),
    OTHER_ROAD_INCIDENT(toSend = "Другое", mapEventType = MapEventType.RoadIncident),

    TRAFFIC_JAM(toSend = "Пробка", mapEventType = MapEventType.TrafficJam),

    WILD_ANIMALS(toSend = "Дикие животные", mapEventType = MapEventType.WildAnimals),

    CAR_ACCIDENT(toSend = "ДТП", mapEventType = MapEventType.CarCrash),

    TRAFFIC_POLICE(toSend = "ГАИ", mapEventType = MapEventType.TrafficPolice),
    WORKING_WITH_RADAR(toSend = "Работают с радаром", mapEventType = MapEventType.TrafficPolice),
    STAY_ON_BEACONS(toSend = "Стоят на маячках", mapEventType = MapEventType.TrafficPolice),
    DOCUMENT_CHECK(toSend = "Проверка документов", mapEventType = MapEventType.TrafficPolice),
    STOPPED_ON_THE_MOVE(toSend = "Остановили на ходу", mapEventType = MapEventType.TrafficPolice),
    SITTING_IN_THE_CAR(toSend = "Сидят в машине", mapEventType = MapEventType.TrafficPolice),
    WORKING_FROM_CAR(toSend = "Работают на ходу", mapEventType = MapEventType.TrafficPolice),
    TRANSPORT_INSPECTION(toSend = "Транспортная инспекция", mapEventType = MapEventType.TrafficPolice),
    FILTER(toSend = "Фильтр", mapEventType = MapEventType.TrafficPolice)
}

fun ReportingOptions.toResource() = when (this) {
    ROAD_REPAIR -> R.string.reporting_item_road_repair
    BROKEN_CAR -> R.string.reporting_item_broken_car
    TOW_TRUCK_WORKING -> R.string.reporting_item_tow_truck_working
    PEDESTRIAN_ON_ROAD -> R.string.reporting_item_pedestrian_on_road
    TRAFFIC_CONTROLLER -> R.string.reporting_item_traffic_controller
    NON_WORKING_TRAFFIC_LIGHTS -> R.string.reporting_item_non_working_traffic_lights
    OPEN_HATCH -> R.string.reporting_item_open_hatch
    ROAD_BLOCKED -> R.string.reporting_item_road_blocked
    OTHER_ROAD_INCIDENT -> R.string.reporting_item_other_road_incident
    TRAFFIC_JAM -> R.string.reporting_item_traffic_jam
    WILD_ANIMALS -> R.string.reporting_item_wild_animals
    CAR_ACCIDENT -> R.string.reporting_item_car_accident
    TRAFFIC_POLICE -> R.string.reporting_item_traffic_police
    WORKING_WITH_RADAR -> R.string.reporting_item_working_with_radar
    STAY_ON_BEACONS -> R.string.reporting_item_stay_on_beacons
    DOCUMENT_CHECK -> R.string.reporting_item_document_check
    STOPPED_ON_THE_MOVE -> R.string.reporting_item_stopped_on_the_move
    SITTING_IN_THE_CAR -> R.string.reporting_item_sitting_in_the_car
    WORKING_FROM_CAR -> R.string.reporting_item_working_from_car
    TRANSPORT_INSPECTION -> R.string.reporting_item_transport_inspection
    FILTER -> R.string.reporting_item_filter
}