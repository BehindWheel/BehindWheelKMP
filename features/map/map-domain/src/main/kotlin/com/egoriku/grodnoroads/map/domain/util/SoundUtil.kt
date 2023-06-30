package com.egoriku.grodnoroads.map.domain.util

import android.content.Context
import com.egoriku.grodnoroads.audioplayer.AudioPlayer
import com.egoriku.grodnoroads.audioplayer.Sound
import java.util.UUID
import kotlin.time.Duration.Companion.minutes

class SoundUtil(context: Context) {

    private val audioPlayer = AudioPlayer(context)
    private val soundHistory = mutableMapOf<String, SoundTimeStamp>()

    fun playOverSpeed() = playSound(Sound.OverSpeed)

    fun playSound(sound: Sound, id: String = UUID.randomUUID().toString()) {
        val currentTimeMillis = System.currentTimeMillis()
        val item = soundHistory[id]

        if (item == null) {
            soundHistory[id] = SoundTimeStamp(sound = sound, timestamp = currentTimeMillis)
            audioPlayer.playSound(sound)
        } else if (currentTimeMillis - item.timestamp > ONE_MINUTE) {
            soundHistory[id] = item.copy(timestamp = currentTimeMillis)
            audioPlayer.playSound(sound)
        }

        invalidateOldSounds()
    }

    private fun invalidateOldSounds() {
        val currentTime = System.currentTimeMillis()
        val invalidIds = soundHistory
            .filterValues { currentTime - it.timestamp > THIRTY_MINUTES }
            .keys

        invalidIds.forEach { soundHistory.remove(it) }
    }

    private data class SoundTimeStamp(
        val sound: Sound,
        val timestamp: Long
    )

    companion object {
        val ONE_MINUTE = 1.minutes.inWholeMilliseconds
        val THIRTY_MINUTES = 30.minutes.inWholeMilliseconds
    }
}