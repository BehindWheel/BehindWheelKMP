package com.egoriku.grodnoroads.shared.models

import androidx.compose.runtime.Stable

@Stable
enum class MapEventType(
    val type: String,
    val emoji: String
) {
    TrafficPolice(type = "police", emoji = "👮"),
    RoadIncident(type = "road_incident", emoji = "❗"),
    CarCrash(type = "car_crash", emoji = "💥🚗"),
    TrafficJam(type = "traffic_jam", emoji = "🚗🚕🚛"),
    WildAnimals(type = "wild_animals", emoji = "🦌"),

    Unsupported(type = "unsupported", emoji = "\uD83D\uDE12");

    companion object {
        fun eventFromString(value: String) = entries.find { it.type == value } ?: Unsupported
    }
}