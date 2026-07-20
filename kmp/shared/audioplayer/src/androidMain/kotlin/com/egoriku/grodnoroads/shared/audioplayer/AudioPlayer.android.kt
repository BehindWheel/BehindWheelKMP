@file:UnstableApi

package com.egoriku.grodnoroads.shared.audioplayer

import android.content.Context
import android.content.IntentFilter
import android.media.AudioManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.audio.AudioFocusRequestCompat
import androidx.media3.common.audio.AudioManagerCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.egoriku.grodnoroads.shared.audioplayer.broadcast.VOLUME_CHANGE_ACTION
import com.egoriku.grodnoroads.shared.audioplayer.broadcast.VolumeChangeReceiver
import com.egoriku.grodnoroads.shared.audioplayer.effect.LoudnessEnhancerEffect
import com.egoriku.grodnoroads.shared.audioplayer.util.audioManagerCompat
import com.egoriku.grodnoroads.shared.audioplayer.util.getStreamMaxVolumeCompat
import com.egoriku.grodnoroads.shared.audioplayer.util.getStreamVolumeCompat
import kotlin.math.roundToInt

@Composable
actual fun rememberAudioPlayer(): AudioPlayer {
    val context = LocalContext.current
    val audioPlayer = remember { AudioPlayer(context) }

    DisposableEffect(audioPlayer) {
        onDispose { audioPlayer.release() }
    }

    return audioPlayer
}

actual class AudioPlayer(private val context: Context) {

    private val audioManager = context.audioManagerCompat
    private var currentVolume = audioManager.getStreamVolumeCompat(AudioManager.STREAM_MUSIC)
    private val maxVolume = audioManager.getStreamMaxVolumeCompat(AudioManager.STREAM_MUSIC)

    private var volumeLevel = 1f

    private val audioFocusRequest =
        AudioFocusRequestCompat.Builder(AudioManagerCompat.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(C.USAGE_ASSISTANCE_NAVIGATION_GUIDANCE)
                    .setContentType(C.AUDIO_CONTENT_TYPE_SPEECH)
                    .build()
            )
            .setWillPauseWhenDucked(false)
            .setOnAudioFocusChangeListener { focusChange ->
                when (focusChange) {
                    AudioManager.AUDIOFOCUS_LOSS,
                    AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                        if (player.isPlaying) player.pause()
                    }
                    AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                        // setWillPauseWhenDucked = false, so we handle ducking manually
                        player.volume = 0.2f
                    }
                    AudioManager.AUDIOFOCUS_GAIN -> {
                        player.volume = 1f
                        if (!player.isPlaying) player.play()
                    }
                }
            }
            .build()

    private val volumeChangeReceiver = VolumeChangeReceiver {
        if (!player.isPlaying) currentVolume = it
    }

    private val player = ExoPlayer.Builder(context)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(C.USAGE_ASSISTANCE_NAVIGATION_GUIDANCE)
                .setContentType(C.AUDIO_CONTENT_TYPE_SPEECH)
                .build(),
            /* handleAudioFocus = */
            false
        )
        .setHandleAudioBecomingNoisy(true)
        .build()

    private var loudnessEnhancer: LoudnessEnhancerEffect? = null
    private var persistedLoudnessGain: Int = 0

    private val playerListener = object : Player.Listener {
        override fun onAudioSessionIdChanged(audioSessionId: Int) {
            if (audioSessionId == C.AUDIO_SESSION_ID_UNSET) return
            loudnessEnhancer?.release()
            loudnessEnhancer = LoudnessEnhancerEffect(audioSessionId).also {
                it.setTargetGain(persistedLoudnessGain)
            }
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            if (playbackState == Player.STATE_ENDED) {
                AudioManagerCompat.abandonAudioFocusRequest(audioManager, audioFocusRequest)
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0)
            }
        }
    }

    init {
        player.addListener(playerListener)

        // Emit the current audio session id if already available
        val currentSessionId = player.audioSessionId
        if (currentSessionId != C.AUDIO_SESSION_ID_UNSET) {
            loudnessEnhancer?.release()
            loudnessEnhancer = LoudnessEnhancerEffect(currentSessionId).also {
                it.setTargetGain(persistedLoudnessGain)
            }
        }

        ContextCompat.registerReceiver(
            context.applicationContext,
            volumeChangeReceiver,
            IntentFilter(VOLUME_CHANGE_ACTION),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    actual fun setVolumeLevel(level: Float) {
        volumeLevel = level
    }

    actual fun setLoudness(loudness: Int) {
        persistedLoudnessGain = loudness * 100
        loudnessEnhancer?.setTargetGain(persistedLoudnessGain)
    }

    actual fun enqueueSound(sound: Sound) {
        val isEnded = player.playbackState == Player.STATE_ENDED
        if (isEnded) {
            player.stop()
            player.clearMediaItems()
        }
        player.addMediaItem(MediaItem.fromUri("asset:///${sound.uri}"))

        if (!player.isPlaying) {
            val result = AudioManagerCompat.requestAudioFocus(audioManager, audioFocusRequest)
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                setSystemVolume()
                player.prepare()
                player.play()
            }
        }
    }

    actual fun playSound(sound: Sound) {
        val result = AudioManagerCompat.requestAudioFocus(audioManager, audioFocusRequest)
        if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) return

        setSystemVolume()

        player.stop()
        player.clearMediaItems()
        player.setMediaItem(MediaItem.fromUri("asset:///${sound.uri}"))
        player.prepare()
        player.play()
    }

    private fun setSystemVolume() {
        audioManager.setStreamVolume(
            /* streamType = */
            AudioManager.STREAM_MUSIC,
            /* index = */
            (maxVolume * volumeLevel).roundToInt(),
            /* flags = */
            0
        )
    }

    actual fun release() {
        player.removeListener(playerListener)
        context.applicationContext.unregisterReceiver(volumeChangeReceiver)
        AudioManagerCompat.abandonAudioFocusRequest(audioManager, audioFocusRequest)
        loudnessEnhancer?.release()
        player.release()
    }
}
