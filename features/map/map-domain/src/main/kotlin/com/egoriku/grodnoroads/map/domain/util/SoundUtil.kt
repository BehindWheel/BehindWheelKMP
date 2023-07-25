package com.egoriku.grodnoroads.map.domain.util

import android.content.Context
import com.egoriku.grodnoroads.audioplayer.AudioPlayer
import com.egoriku.grodnoroads.audioplayer.Sound
import com.egoriku.grodnoroads.map.domain.model.CameraType
import com.egoriku.grodnoroads.map.domain.model.MapEventType
import java.util.UUID
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class SoundUtil(context: Context) {

    private val audioPlayer = AudioPlayer(context)
    private val soundHistory = mutableMapOf<String, SoundTimeStamp>()

    private val overSpeedId = UUID.randomUUID().toString()

    fun playOverSpeed() = playSound(
        sound = Sound.OverSpeed,
        id = overSpeedId,
        expiration = FIVE_SECONDS
    )

    fun playIncident(id: String, mapEventType: MapEventType) {
        when (mapEventType) {
            MapEventType.RoadAccident -> Sound.RoadIncident
            MapEventType.TrafficPolice -> Sound.TrafficPolice
            MapEventType.RoadIncident -> Sound.RoadIncident
            MapEventType.CarCrash -> Sound.CarCrash
            MapEventType.TrafficJam -> Sound.TrafficJam
            MapEventType.WildAnimals -> Sound.WildAnimals
            MapEventType.Unsupported -> null
        }?.let { playSound(sound = it, id = id) }
    }

    fun playCameraLimit(id: String, speedLimit: Int, cameraType: CameraType) {
        val speedLimitSound = when (speedLimit) {
            40 -> Sound.SpeedLimit40
            50 -> Sound.SpeedLimit50
            60 -> Sound.SpeedLimit60
            70 -> Sound.SpeedLimit70
            80 -> Sound.SpeedLimit80
            90 -> Sound.SpeedLimit90
            100 -> Sound.SpeedLimit100
            110 -> Sound.SpeedLimit110
            120 -> Sound.SpeedLimit120
            else -> null
        }
        val cameraSound = when (cameraType) {
            CameraType.StationaryCamera -> Sound.StationaryCamera
            CameraType.MobileCamera -> Sound.MobileCamera
            CameraType.MediumSpeedCamera -> Sound.MediumSpeedCamera
        }

        playSound(sound = cameraSound, id = id)
        speedLimitSound?.run { playSound(sound = this, id = "camera_$id") }
    }

    private fun playSound(
        sound: Sound,
        id: String = UUID.randomUUID().toString(),
        expiration: Long = ONE_MINUTE
    ) {
        val currentTimeMillis = System.currentTimeMillis()
        val item = soundHistory[id]

        if (item == null) {
            soundHistory[id] = SoundTimeStamp(sound = sound, timestamp = currentTimeMillis)
            audioPlayer.enqueueSound(sound)
        } else if (currentTimeMillis - item.timestamp > expiration) {
            soundHistory[id] = item.copy(timestamp = currentTimeMillis)
            audioPlayer.enqueueSound(sound)
        }

        invalidateOldSounds()
    }

    private fun invalidateOldSounds() {
        val currentTime = System.currentTimeMillis()
        val invalidIds = soundHistory
            .filterValues { currentTime - it.timestamp > THIRTY_MINUTES }
            .keys

        invalidIds.forEach(soundHistory::remove)
    }

    private data class SoundTimeStamp(
        val sound: Sound,
        val timestamp: Long
    )

    companion object {
        val FIVE_SECONDS = 5.seconds.inWholeMilliseconds
        val ONE_MINUTE = 3.minutes.inWholeMilliseconds
        val THIRTY_MINUTES = 30.minutes.inWholeMilliseconds
    }
}