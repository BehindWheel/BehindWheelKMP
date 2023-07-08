package com.egoriku.grodnoroads.audioplayer

sealed class Sound(val assetPath: String) {
    object OverSpeed : Sound(assetPath = "sound_over_speed.mp3")

    object StationaryCamera : Sound(assetPath = "camera/stationary_camera.ogg")
    object MobileCamera : Sound(assetPath = "camera/mobile_camera.ogg")
    object MediumSpeedCamera : Sound(assetPath = "camera/medium_speed_camera.ogg")

    object SpeedLimit40 : Sound(assetPath = "limit/40.ogg")
    object SpeedLimit50 : Sound(assetPath = "limit/50.ogg")
    object SpeedLimit60 : Sound(assetPath = "limit/60.ogg")
    object SpeedLimit70 : Sound(assetPath = "limit/70.ogg")
    object SpeedLimit80 : Sound(assetPath = "limit/80.ogg")
    object SpeedLimit90 : Sound(assetPath = "limit/90.ogg")
    object SpeedLimit100 : Sound(assetPath = "limit/100.ogg")
    object SpeedLimit110 : Sound(assetPath = "limit/110.ogg")
    object SpeedLimit120 : Sound(assetPath = "limit/120.ogg")

    object TrafficPolice : Sound(assetPath = "incident/traffic_police.ogg")
    object CarCrash : Sound(assetPath = "incident/car_crash.ogg")
    object WildAnimals : Sound(assetPath = "incident/wild_animals.ogg")
    object TrafficJam : Sound(assetPath = "incident/traffic_jam.ogg")
    object RoadIncident : Sound(assetPath = "incident/road_incident.ogg")
}