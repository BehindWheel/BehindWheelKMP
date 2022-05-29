package com.egoriku.grodnoroads.screen.map.domain

enum class MapEventType(
    val type: String,
    val emoji: String
) {
    StationaryCamera(type = "stationary", emoji = "📷"),
    MobileCamera(type = "mobile", emoji = "📸"),
    TrafficPolice(type = "police", emoji = "👮"),

    RoadAccident(type = "accident", emoji = "❗"),
    RoadRepair(type = "road_repair", emoji = "🚧"),
    TrafficJam(type = "traffic_jam", emoji = "🚗🚕🚛"),
    WildAnimals(type = "wild_animals", emoji = "🦌"),
    CarCrash(type = "car_crash", emoji = "💥🚗");

    companion object {
        fun eventFromString(value: String): MapEventType {
            return requireNotNull(values().find { it.type == value })
        }
    }
}