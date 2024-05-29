package com.egoriku.grodnoroads.shared.audioplayer

import android.content.Context
import android.content.IntentFilter
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.audiofx.LoudnessEnhancer
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.media.AudioFocusRequestCompat
import androidx.media.AudioManagerCompat
import com.egoriku.grodnoroads.shared.audioplayer.broadcast.VOLUME_CHANGE_ACTION
import com.egoriku.grodnoroads.shared.audioplayer.broadcast.VolumeChangeReceiver
import com.egoriku.grodnoroads.shared.audioplayer.util.AudioEffectUtil
import com.egoriku.grodnoroads.shared.audioplayer.util.audioManager
import kotlin.math.roundToInt

@Composable
actual fun rememberAudioPlayer(): AudioPlayer {
    val context = LocalContext.current
    return remember { AudioPlayer(context) }
}

actual class AudioPlayer(private val context: Context) {

    private val audioManager = context.audioManager

    private val audioFocusRequest =
        AudioFocusRequestCompat.Builder(AudioManagerCompat.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE)
            .setOnAudioFocusChangeListener {}
            .build()

    private val mediaPlayer = MediaPlayer()
    private val loudnessEnhancer: LoudnessEnhancer
    private val soundQueue = mutableListOf<Sound>()
    private var isPlaying = false

    private var currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    private val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

    private var volumeLevel = 1f

    private val volumeChangeReceiver = VolumeChangeReceiver {
        if (!isPlaying) currentVolume = it
    }

    init {
        mediaPlayer.apply {
            setVolume(1f, 1f)
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_NAVIGATION_GUIDANCE)
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .build()
            )
            setOnCompletionListener {
                if (soundQueue.isEmpty()) {
                    AudioManagerCompat.abandonAudioFocusRequest(audioManager, audioFocusRequest)
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0)
                }
                enqueueNextSound()
            }
        }
        loudnessEnhancer = LoudnessEnhancer(mediaPlayer.audioSessionId).apply { enabled = true }
        ContextCompat.registerReceiver(
            /* context = */ context.applicationContext,
            /* receiver = */ volumeChangeReceiver,
            /* filter = */ IntentFilter(VOLUME_CHANGE_ACTION),
            /* flags = */ ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    actual fun setVolumeLevel(level: Float) {
        volumeLevel = level
    }

    actual fun setLoudness(loudness: Int) {
        if (!AudioEffectUtil.isLoudnessEnhancerAvailable()) {
            return
        }
        loudnessEnhancer.setTargetGain(loudness * 100)
    }

    actual fun enqueueSound(sound: Sound) {
        soundQueue.add(sound)
        if (!isPlaying) enqueueNextSound()
    }

    actual fun playSound(sound: Sound) {
        AudioManagerCompat.requestAudioFocus(audioManager, audioFocusRequest)
        audioManager.setStreamVolume(
            /* streamType = */ AudioManager.STREAM_MUSIC,
            /* index = */ (maxVolume * volumeLevel).roundToInt(),
            /* flags = */ 0
        )

        mediaPlayer.reset()

        val assetFileDescriptor = context.assets.openFd(sound.uri)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mediaPlayer.setDataSource(assetFileDescriptor)
        } else {
            mediaPlayer.setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.length
            )
        }
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    private fun enqueueNextSound() {
        isPlaying = when {
            soundQueue.isNotEmpty() -> {
                playSound(soundQueue.removeAt(0))
                true
            }
            else -> false
        }
    }

    actual fun release() {
        context.applicationContext.unregisterReceiver(volumeChangeReceiver)
        AudioManagerCompat.abandonAudioFocusRequest(audioManager, audioFocusRequest)
        mediaPlayer.release()
        soundQueue.clear()
    }
}