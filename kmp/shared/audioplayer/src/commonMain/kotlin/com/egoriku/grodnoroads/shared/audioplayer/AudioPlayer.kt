package com.egoriku.grodnoroads.shared.audioplayer

import androidx.compose.runtime.Composable

@Composable
expect fun rememberAudioPlayer(): AudioPlayer

expect class AudioPlayer {
    fun setVolumeLevel(level: Float)
    fun setLoudness(loudness: Int)
    fun enqueueSound(sound: Sound)
    fun playSound(sound: Sound)

    fun release()
}
