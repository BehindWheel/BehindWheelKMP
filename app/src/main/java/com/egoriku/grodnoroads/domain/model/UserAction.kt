package com.egoriku.grodnoroads.domain.model

enum class UserAction(val type: String) {
    Police(type = "police"),
    Accident(type = "accident")
}