package com.egoriku.grodnoroads.audioplayer

sealed class Sound(val assetPath: String) {
    object OverSpeed : Sound(assetPath = "sound_over_speed.mp3")

    object StationaryCamera : Sound(assetPath = "camera/stationary_camera.mp3")
    object MobileCamera : Sound(assetPath = "camera/mobile_camera.mp3")
    object MediumSpeedCamera : Sound(assetPath = "camera/medium_speed_camera.mp3")

    object SpeedLimit40 : Sound(assetPath = "limit/40.mp3")
    object SpeedLimit50 : Sound(assetPath = "limit/50.mp3")
    object SpeedLimit60 : Sound(assetPath = "limit/60.mp3")
    object SpeedLimit70 : Sound(assetPath = "limit/70.mp3")
    object SpeedLimit80 : Sound(assetPath = "limit/80.mp3")
    object SpeedLimit90 : Sound(assetPath = "limit/90.mp3")
    object SpeedLimit100 : Sound(assetPath = "limit/100.mp3")
    object SpeedLimit110 : Sound(assetPath = "limit/110.mp3")
    object SpeedLimit120 : Sound(assetPath = "limit/120.mp3")

    object TrafficPolice : Sound(assetPath = "incident/traffic_police.mp3")
    object CarCrash : Sound(assetPath = "incident/car_crash.mp3")
    object WildAnimals : Sound(assetPath = "incident/wild_animals.mp3")
    object TrafficJam : Sound(assetPath = "incident/traffic_jam.mp3")
    object RoadIncident : Sound(assetPath = "incident/road_incident.mp3")
}