package com.egoriku.grodnoroads.shared.audioplayer

import com.egoriku.grodnoroads.compose.resources.Res

sealed class Sound(val uri: String) {
    data object OverSpeed : Sound(uri = Res.platformUri("files/sound_over_speed.mp3"))

    data object StationaryCamera : Sound(uri = Res.platformUri("files/camera/stationary_camera.mp3"))

    data object MobileCamera : Sound(uri = Res.platformUri("files/camera/mobile_camera.mp3"))
    data object MediumSpeedCamera : Sound(uri = Res.platformUri("files/camera/medium_speed_camera.mp3"))

    data object SpeedLimit40 : Sound(uri = Res.platformUri("files/limit/limit_40.mp3"))
    data object SpeedLimit50 : Sound(uri = Res.platformUri("files/limit/limit_50.mp3"))
    data object SpeedLimit60 : Sound(uri = Res.platformUri("files/limit/limit_60.mp3"))
    data object SpeedLimit70 : Sound(uri = Res.platformUri("files/limit/limit_70.mp3"))
    data object SpeedLimit80 : Sound(uri = Res.platformUri("files/limit/limit_80.mp3"))
    data object SpeedLimit90 : Sound(uri = Res.platformUri("files/limit/limit_90.mp3"))
    data object SpeedLimit100 : Sound(uri = Res.platformUri("files/limit/limit_100.mp3"))
    data object SpeedLimit110 : Sound(uri = Res.platformUri("files/limit/limit_110.mp3"))
    data object SpeedLimit120 : Sound(uri = Res.platformUri("files/limit/limit_120.mp3"))

    data object TrafficPolice : Sound(uri = Res.platformUri("files/incident/traffic_police.mp3"))
    data object CarCrash : Sound(uri = Res.platformUri("files/incident/car_crash.mp3"))
    data object WildAnimals : Sound(uri = Res.platformUri("files/incident/wild_animals.mp3"))
    data object TrafficJam : Sound(uri = Res.platformUri("files/incident/traffic_jam.mp3"))
    data object RoadIncident : Sound(uri = Res.platformUri("files/incident/road_incident.mp3"))

    data object TestAudioLevel : Sound(uri = Res.platformUri("files/test/test_audio_level.mp3"))
}

expect fun Res.platformUri(path: String): String
