package com.egoriku.grodnoroads.audioplayer

sealed class Sound(val assetPath: String) {
    object OverSpeed : Sound(assetPath = "sound_overspeed.mp3")
}