package com.egoriku.grodnoroads.shared.appsettings.types.alert

import com.egoriku.grodnoroads.resources.R

enum class VolumeLevel(val levelName: String, val level: Float) {
    Low(levelName = "low", level = 0.3f),
    Medium(levelName = "medium", level = 0.6f),
    High(levelName = "high", level = 1f);

    companion object {
        fun VolumeLevel.toResource(): Int = when (this) {
            Low -> R.string.alerts_volume_level_low
            Medium -> R.string.alerts_volume_level_medium
            High -> R.string.alerts_volume_level_high
        }
    }
}