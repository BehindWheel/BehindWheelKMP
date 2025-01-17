package com.egoriku.grodnoroads.guidance.screen.sound

import com.egoriku.grodnoroads.extensions.DateTime
import com.egoriku.grodnoroads.extensions.Uuid
import com.egoriku.grodnoroads.guidance.domain.model.CameraType
import com.egoriku.grodnoroads.shared.audioplayer.Sound
import com.egoriku.grodnoroads.shared.models.MapEventType
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

interface SoundController {

    fun playOverSpeed()
    fun playIncident(id: String, mapEventType: MapEventType)
    fun playCameraLimit(id: String, speedLimit: Int, cameraType: CameraType)

    fun setVolume(level: Float)
    fun setLoudness(loudness: Int)
}

abstract class SharedSoundController : SoundController {
    private val soundHistory = mutableMapOf<String, SoundTimeStamp>()
    private val overSpeedId = Uuid.random()

    private val currentTimeMillis: Long
        get() = DateTime.currentTimeMillis()

    abstract fun enqueueSound(sound: Sound)

    override fun playOverSpeed() = playSound(
        sound = Sound.OverSpeed,
        id = overSpeedId,
        expiration = FIVE_SECONDS
    )

    override fun playIncident(id: String, mapEventType: MapEventType) {
        val incidentSound = when (mapEventType) {
            MapEventType.TrafficPolice -> Sound.TrafficPolice
            MapEventType.RoadIncident -> Sound.RoadIncident
            MapEventType.CarCrash -> Sound.CarCrash
            MapEventType.TrafficJam -> Sound.TrafficJam
            MapEventType.WildAnimals -> Sound.WildAnimals
            MapEventType.Unsupported -> null
        } ?: return

        val isSkipByDuplicate = checkDuplicate(incidentSound)

        when {
            isSkipByDuplicate -> return
            else -> playSound(sound = incidentSound, id = id)
        }
    }

    override fun playCameraLimit(id: String, speedLimit: Int, cameraType: CameraType) {
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

        val isSkipByDuplicate = checkDuplicate(cameraSound)
        when {
            isSkipByDuplicate -> return
            else -> {
                playSound(sound = cameraSound, id = id)
                speedLimitSound?.run { playSound(sound = this, id = "camera_$id") }
            }
        }
    }

    private fun playSound(
        sound: Sound,
        id: String = Uuid.random(),
        expiration: Long = FIVE_MINUTE
    ) {
        val currentTimeMillis = currentTimeMillis
        val item = soundHistory[id]

        if (item == null) {
            soundHistory[id] = SoundTimeStamp(sound = sound, timestamp = currentTimeMillis)
            enqueueSound(sound)
        } else if (currentTimeMillis - item.timestamp > expiration) {
            soundHistory[id] = item.copy(timestamp = currentTimeMillis)
            enqueueSound(sound)
        }

        invalidateOldSounds()
    }

    private fun checkDuplicate(sound: Sound, expiration: Long = ONE_MINUTE): Boolean {
        val currentTimeMillis = currentTimeMillis
        return soundHistory
            .filterValues { soundTimeStamp ->
                soundTimeStamp.sound == sound &&
                    soundTimeStamp.timestamp >= currentTimeMillis - expiration
            }
            .isNotEmpty()
    }

    private fun invalidateOldSounds() {
        val currentTime = currentTimeMillis
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
        val ONE_MINUTE = 30.seconds.inWholeMilliseconds
        val FIVE_MINUTE = 5.minutes.inWholeMilliseconds
        val THIRTY_MINUTES = 30.minutes.inWholeMilliseconds
    }
}
