package com.egoriku.grodnoroads.guidance.screen.sound

import android.content.Context
import com.egoriku.grodnoroads.shared.audioplayer.AudioPlayer
import com.egoriku.grodnoroads.shared.audioplayer.Sound

class AndroidSoundController(context: Context) : SharedSoundController() {

    private val audioPlayer = AudioPlayer(context)

    override fun setVolume(level: Float) = audioPlayer.setVolumeLevel(level)
    override fun setLoudness(loudness: Int) = audioPlayer.setLoudness(loudness)

    override fun enqueueSound(sound: Sound) = audioPlayer.enqueueSound(sound)
}
