package com.egoriku.grodnoroads.eventreporting.domain

import com.egoriku.grodnoroads.shared.models.MapEventType

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