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

    abstract fun enqueueSound(sound: Sound)

    override fun playOverSpeed() {
        if (tracker.shouldPlay(overSpeedId, PlayedAlertTracker.FIVE_SECONDS)) {
            tracker.record(overSpeedId)
            enqueueSound(Sound.OverSpeed)
        }
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

        if (!tracker.shouldPlay(id)) return

        tracker.record(id)
        enqueueSound(incidentSound)
        tracker.cleanup()
    }

    override fun playCameraLimit(id: String, speedLimit: Int, cameraType: CameraType) {
        if (!tracker.shouldPlay(id)) return

        val cameraSound = when (cameraType) {
            CameraType.StationaryCamera -> Sound.StationaryCamera
            CameraType.MobileCamera -> Sound.MobileCamera
            CameraType.MediumSpeedCamera -> Sound.MediumSpeedCamera
        }

        tracker.record(id)
        enqueueSound(cameraSound)

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
            val speedLimitId = "camera_$id"
            if (tracker.shouldPlay(speedLimitId)) {
                tracker.record(speedLimitId)
                enqueueSound(it)
            }
        }

        tracker.cleanup()
    }
}
