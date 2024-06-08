package com.egoriku.grodnoroads.guidance.screen.sound

import com.egoriku.grodnoroads.shared.audioplayer.AudioPlayer
import com.egoriku.grodnoroads.shared.audioplayer.Sound

class IosSoundController : SharedSoundController() {

    private val audioPlayer = AudioPlayer()

    override fun setVolume(level: Float) = audioPlayer.setVolumeLevel(level)
    override fun setLoudness(loudness: Int) = audioPlayer.setLoudness(loudness)

    override fun enqueueSound(sound: Sound) = audioPlayer.enqueueSound(sound)
}