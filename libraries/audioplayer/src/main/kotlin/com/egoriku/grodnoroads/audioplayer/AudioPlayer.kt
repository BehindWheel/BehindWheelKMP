package com.egoriku.grodnoroads.audioplayer

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import androidx.core.net.toUri
import androidx.media.AudioFocusRequestCompat
import androidx.media.AudioManagerCompat
import com.egoriku.grodnoroads.extensions.audioManager

class AudioPlayer(private val context: Context) {

    private val audioManager = context.audioManager
    private val audioFocusRequest =
        AudioFocusRequestCompat.Builder(AudioManagerCompat.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
            .setOnAudioFocusChangeListener {}
            .build()

    private val mediaPlayer = MediaPlayer()
    private val soundQueue = mutableListOf<Sound>()
    private var isPlaying = false

    init {
        mediaPlayer.apply {
            setVolume(1f, 1f)
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT)
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .build()
            )
            setOnPreparedListener {
                AudioManagerCompat.requestAudioFocus(audioManager, audioFocusRequest)
            }
            setOnCompletionListener {
                AudioManagerCompat.abandonAudioFocusRequest(audioManager, audioFocusRequest)
                playNextSound()
            }
        }
    }

    fun playSound(sound: Sound) {
        soundQueue.add(sound)
        if (!isPlaying) {
            playNextSound()
        }
    }

    private fun playNextSound() {
        if (soundQueue.isNotEmpty()) {
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