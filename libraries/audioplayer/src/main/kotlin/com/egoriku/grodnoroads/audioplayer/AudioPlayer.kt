package com.egoriku.grodnoroads.audioplayer

import android.content.Context
import android.content.IntentFilter
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import androidx.core.net.toUri
import androidx.media.AudioFocusRequestCompat
import androidx.media.AudioManagerCompat
import com.egoriku.grodnoroads.audioplayer.broadcast.VOLUME_CHANGE_ACTION
import com.egoriku.grodnoroads.audioplayer.broadcast.VolumeChangeReceiver
import com.egoriku.grodnoroads.extensions.audioManager

class AudioPlayer(private val context: Context) {

    private val audioManager = context.audioManager

    private val audioFocusRequest =
        AudioFocusRequestCompat.Builder(AudioManagerCompat.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE)
            .setOnAudioFocusChangeListener {}
            .build()

    private val mediaPlayer = MediaPlayer()
    private val soundQueue = mutableListOf<Sound>()
    private var isPlaying = false

    private var currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    private val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

    private val volumeChangeReceiver = VolumeChangeReceiver {
        if (!isPlaying) currentVolume = it
    }

    init {
        mediaPlayer.apply {
            setVolume(1f, 1f)
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT)
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .build()
            )
            setOnCompletionListener {
                if (soundQueue.isEmpty()) {
                    AudioManagerCompat.abandonAudioFocusRequest(audioManager, audioFocusRequest)
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0)
                }
                playNextSound()
            }
        }
        context.applicationContext.registerReceiver(
            volumeChangeReceiver,
            IntentFilter(VOLUME_CHANGE_ACTION)
        )
    }

    fun playSound(sound: Sound) {
        soundQueue.add(sound)
        if (!isPlaying) {
            playNextSound()
        }
    }

    private fun playNextSound() {
        if (soundQueue.isNotEmpty()) {
            AudioManagerCompat.requestAudioFocus(audioManager, audioFocusRequest)
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0)

            val sound = soundQueue.removeAt(0)
            mediaPlayer.reset()

            val assetFileDescriptor = context.assets.openFd(sound.assetPath)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mediaPlayer.setDataSource(assetFileDescriptor)
            } else {
                mediaPlayer.setDataSource(
                    context,
                    "android.resource://${context.packageName}/${assetFileDescriptor.fileDescriptor}".toUri()
                )
            }
            mediaPlayer.prepare()
            mediaPlayer.start()
            isPlaying = true
        } else {
            isPlaying = false
        }
    }
}