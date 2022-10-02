package com.egoriku.grodnoroads.map.domain.model

enum class MapEventType(
    val type: String,
    val emoji: String
) {
    StationaryCamera(type = "stationary", emoji = "📷"),
    MobileCamera(type = "mobile", emoji = "📸"),

    @Deprecated("should be removed in next release")
    RoadAccident(type = "accident", emoji = "❗"),

    TrafficPolice(type = "police", emoji = "👮"),
    RoadIncident(type = "road_incident", emoji = "❗"),
    CarCrash(type = "car_crash", emoji = "💥🚗"),
    TrafficJam(type = "traffic_jam", emoji = "🚗🚕🚛"),
    WildAnimals(type = "wild_animals", emoji = "🦌");

    companion object {
        fun eventFromString(value: String): MapEventType {
            return requireNotNull(values().find { it.type == value })
        }
    }
}