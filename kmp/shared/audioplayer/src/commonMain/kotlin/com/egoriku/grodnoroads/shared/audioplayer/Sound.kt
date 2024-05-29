package com.egoriku.grodnoroads.shared.audioplayer

sealed class Sound(val uri: String) {
    data object OverSpeed : Sound(uri = "sound_over_speed.mp3")

    data object StationaryCamera : Sound(uri = "camera/stationary_camera.mp3")
    data object MobileCamera : Sound(uri = "camera/mobile_camera.mp3")
    data object MediumSpeedCamera : Sound(uri = "camera/medium_speed_camera.mp3")

    data object SpeedLimit40 : Sound(uri = "limit/40.mp3")
    data object SpeedLimit50 : Sound(uri = "limit/50.mp3")
    data object SpeedLimit60 : Sound(uri = "limit/60.mp3")
    data object SpeedLimit70 : Sound(uri = "limit/70.mp3")
    data object SpeedLimit80 : Sound(uri = "limit/80.mp3")
    data object SpeedLimit90 : Sound(uri = "limit/90.mp3")
    data object SpeedLimit100 : Sound(uri = "limit/100.mp3")
    data object SpeedLimit110 : Sound(uri = "limit/110.mp3")
    data object SpeedLimit120 : Sound(uri = "limit/120.mp3")

    data object TrafficPolice : Sound(uri = "incident/traffic_police.mp3")
    data object CarCrash : Sound(uri = "incident/car_crash.mp3")
    data object WildAnimals : Sound(uri = "incident/wild_animals.mp3")
    data object TrafficJam : Sound(uri = "incident/traffic_jam.mp3")
    data object RoadIncident : Sound(uri = "incident/road_incident.mp3")

    data object TestAudioLevel : Sound(uri = "test/test_audio_level.mp3")
}