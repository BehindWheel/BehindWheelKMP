package com.egoriku.grodnoroads.guidance.screen.sound

import com.egoriku.grodnoroads.extensions.Uuid
import com.egoriku.grodnoroads.guidance.domain.model.CameraType
import com.egoriku.grodnoroads.shared.audioplayer.Sound
import com.egoriku.grodnoroads.shared.models.MapEventType

interface SoundController {

    fun playOverSpeed()
    fun playIncident(id: String, mapEventType: MapEventType)
    fun playCameraLimit(id: String, speedLimit: Int, cameraType: CameraType)

    fun setVolume(level: Float)
    fun setLoudness(loudness: Int)
}

abstract class SharedSoundController(
    private val tracker: PlayedAlertTracker = PlayedAlertTracker()
) : SoundController {
    private val overSpeedId = Uuid.random()

    abstract fun enqueueSound(sound: Sound): Boolean

    override fun playOverSpeed() {
        playIfAllowed(overSpeedId, Sound.OverSpeed, PlayedAlertTracker.FIVE_SECONDS)
        tracker.cleanup()
    }

    override fun playIncident(id: String, mapEventType: MapEventType) {
        val incidentSound = when (mapEventType) {
            MapEventType.TrafficPolice -> Sound.TrafficPolice
            MapEventType.RoadIncident -> Sound.RoadIncident
            MapEventType.CarCrash -> Sound.CarCrash
            MapEventType.TrafficJam -> Sound.TrafficJam
            MapEventType.WildAnimals -> Sound.WildAnimals
            MapEventType.Unsupported -> null
        } ?: return

        playIfAllowed(id, incidentSound)
        tracker.cleanup()
    }

    override fun playCameraLimit(id: String, speedLimit: Int, cameraType: CameraType) {
        val cameraSound = when (cameraType) {
            CameraType.StationaryCamera -> Sound.StationaryCamera
            CameraType.MobileCamera -> Sound.MobileCamera
            CameraType.MediumSpeedCamera -> Sound.MediumSpeedCamera
        }

        playIfAllowed(id, cameraSound)

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
        speedLimitSound?.let {
            playIfAllowed("camera_$id", it)
        }

        tracker.cleanup()
    }

    private fun playIfAllowed(
        id: String,
        sound: Sound,
        expiration: Long = PlayedAlertTracker.FIVE_MINUTES
    ) {
        if (tracker.shouldPlay(id, expiration)) {
            if (enqueueSound(sound)) {
                tracker.record(id)
            }
        }
    }
}
