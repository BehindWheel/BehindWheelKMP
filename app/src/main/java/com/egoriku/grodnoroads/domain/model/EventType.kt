package com.egoriku.grodnoroads.domain.model

enum class EventType(val type: String) {
    StationaryCamera(type = "stationary"),
    MobileCamera(type = "mobile"),
    Police(type = "police"),
    Accident(type = "accident");

    companion object {
        fun eventFromString(value: String): EventType {
            return requireNotNull(values().find { it.type == value })
        }
    }
}