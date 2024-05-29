package com.egoriku.grodnoroads.shared.audioplayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberAudioPlayer(): AudioPlayer {
    return remember { AudioPlayer() }
}

// TODO: use AVAudioPlayer
actual class AudioPlayer {
    actual fun setVolumeLevel(level: Float) {
    }

    actual fun setLoudness(loudness: Int) {
    }

    actual fun enqueueSound(sound: Sound) {
    }

    actual fun playSound(sound: Sound) {
    }

    actual fun release() {
    }
}
