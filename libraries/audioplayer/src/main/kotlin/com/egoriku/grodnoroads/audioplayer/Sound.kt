package com.egoriku.grodnoroads.audioplayer

sealed class Sound(val path: String) {
    data object OverSpeed : Sound(path = "sound_over_speed.mp3")

    data object StationaryCamera : Sound(path = "camera/stationary_camera.mp3")
    data object MobileCamera : Sound(path = "camera/mobile_camera.mp3")
    data object MediumSpeedCamera : Sound(path = "camera/medium_speed_camera.mp3")

    data object SpeedLimit40 : Sound(path = "limit/40.mp3")
    data object SpeedLimit50 : Sound(path = "limit/50.mp3")
    data object SpeedLimit60 : Sound(path = "limit/60.mp3")
    data object SpeedLimit70 : Sound(path = "limit/70.mp3")
    data object SpeedLimit80 : Sound(path = "limit/80.mp3")
    data object SpeedLimit90 : Sound(path = "limit/90.mp3")
    data object SpeedLimit100 : Sound(path = "limit/100.mp3")
    data object SpeedLimit110 : Sound(path = "limit/110.mp3")
    data object SpeedLimit120 : Sound(path = "limit/120.mp3")

    data object TrafficPolice : Sound(path = "incident/traffic_police.mp3")
    data object CarCrash : Sound(path = "incident/car_crash.mp3")
    data object WildAnimals : Sound(path = "incident/wild_animals.mp3")
    data object TrafficJam : Sound(path = "incident/traffic_jam.mp3")
    data object RoadIncident : Sound(path = "incident/road_incident.mp3")

    data object TestAudioLevel : Sound(path = "test/test_audio_level.mp3")
}