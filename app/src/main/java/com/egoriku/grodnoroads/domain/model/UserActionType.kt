package com.egoriku.grodnoroads.domain.model

enum class UserActionType(val type: String) {
    Police(type = "police"),
    Accident(type = "accident");

    companion object {
        fun valueOf(value: String): UserActionType {
            return requireNotNull(values().find { it.type == value })
        }
    }
}