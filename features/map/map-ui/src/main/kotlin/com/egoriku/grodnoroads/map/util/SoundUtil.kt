package com.egoriku.grodnoroads.map.util

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import androidx.media.AudioFocusRequestCompat
import androidx.media.AudioManagerCompat
import com.egoriku.grodnoroads.extensions.audioManager
import com.egoriku.grodnoroads.map.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class SoundUtil(context: Context) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val audioManager = context.audioManager
    private val audioFocusRequest =
        AudioFocusRequestCompat.Builder(AudioManagerCompat.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
            .setOnAudioFocusChangeListener {}
            .build()

    private val audioAttributes = AudioAttributes.Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
        .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT)
        .setLegacyStreamType(AudioManager.STREAM_MUSIC)
        .build()

    private val soundPool = SoundPool.Builder()
        .setAudioAttributes(audioAttributes)
        .build()

    private val overSpeedSound = soundPool.load(context, R.raw.sound_overspeed, 1)

    fun playOverSpeedSoundSound() = playSound(overSpeedSound)

    private fun playSound(soundId: Int) {
        coroutineScope.launch {
            AudioManagerCompat.requestAudioFocus(audioManager, audioFocusRequest)
            delay(1.seconds)
            soundPool.play(soundId, SOUND_VOLUME_MULTIPLIER, SOUND_VOLUME_MULTIPLIER, 1, 0, 1f)
            delay(2.seconds)
            AudioManagerCompat.abandonAudioFocusRequest(audioManager, audioFocusRequest)
        }
    }

    companion object {
        private const val SOUND_VOLUME_MULTIPLIER = 1f
    }
}