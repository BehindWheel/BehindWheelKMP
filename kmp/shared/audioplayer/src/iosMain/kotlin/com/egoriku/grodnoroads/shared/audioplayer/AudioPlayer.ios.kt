package com.egoriku.grodnoroads.shared.audioplayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import platform.AVFAudio.AVAudioPlayer
import platform.AVFAudio.AVAudioPlayerDelegateProtocol
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryPlayback
import platform.AVFAudio.AVAudioSessionSetActiveOptionNotifyOthersOnDeactivation
import platform.AVFAudio.setActive
import platform.darwin.NSObject

@Composable
actual fun rememberAudioPlayer(): AudioPlayer {
    return remember { AudioPlayer() }
}

actual class AudioPlayer {
    private val scope = CoroutineScope(Dispatchers.Default)

    private var audioPlayer: AVAudioPlayer? = null

    private val soundQueue = mutableListOf<Sound>()
    private var isPlaying = false

    private val delegate = object : NSObject(), AVAudioPlayerDelegateProtocol {
        override fun audioPlayerDidFinishPlaying(player: AVAudioPlayer, successfully: Boolean) {
            enqueueNextSound()
        }
    }

    private val audioSession = AVAudioSession.sharedInstance()

    init {
        audioSession.setCategory(AVAudioSessionCategoryPlayback, null)
        audioSession.setActive(active = false, error = null)
        audioPlayer?.volume = 1f
    }

    actual fun setVolumeLevel(level: Float) {
        audioPlayer?.volume = level
    }

    actual fun setLoudness(loudness: Int) {
        // Not supported on iOS
    }

    actual fun enqueueSound(sound: Sound) {
        soundQueue.add(sound)
        if (!isPlaying) enqueueNextSound()
    }

    actual fun playSound(sound: Sound) {
        scope.launch {
            audioSession.setActive(active = true, error = null)
            audioPlayer = AVAudioPlayer(sound.assetResource.url, null)
            audioPlayer?.delegate = delegate
            audioPlayer?.play()
        }
    }

    actual fun release() {
        audioPlayer?.stop()
        audioPlayer?.delegate = null
        audioPlayer = null

        audioSession.setCategory(null, null)
        audioSession.setActive(active = false, error = null)
    }

    private fun enqueueNextSound() {
        isPlaying = when {
            soundQueue.isNotEmpty() -> {
                playSound(soundQueue.removeAt(0))
                true
            }
            else -> {
                audioSession.setActive(
                    active = false,
                    withOptions = AVAudioSessionSetActiveOptionNotifyOthersOnDeactivation,
                    error = null
                )
                false
            }
        }
    }
}
