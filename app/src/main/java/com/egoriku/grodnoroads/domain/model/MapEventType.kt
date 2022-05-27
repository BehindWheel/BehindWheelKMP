package com.egoriku.grodnoroads.domain.model

enum class MapEventType(val type: String) {
    StationaryCamera(type = "stationary"),
    MobileCamera(type = "mobile"),
    TrafficPolice(type = "police"),
    RoadAccident(type = "accident");

    companion object {
        fun eventFromString(value: String): MapEventType {
            return requireNotNull(values().find { it.type == value })
        }
    }
}