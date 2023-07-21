package com.egoriku.grodnoroads.shared.appsettings.types.alert

import com.egoriku.grodnoroads.resources.R

enum class VolumeLevel(val level: String) {
    Low("low"),
    Medium("medium"),
    High("high");

    companion object {
        fun VolumeLevel.toResource(): Int = when (this) {
            Low -> R.string.alerts_volume_level_low
            Medium -> R.string.alerts_volume_level_medium
            High -> R.string.alerts_volume_level_high
        }
    }
}