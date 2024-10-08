package com.egoriku.grodnoroads.shared.audioplayer

import com.egoriku.grodnoroads.shared.resources.MR
import dev.icerock.moko.resources.AssetResource

sealed class Sound(val assetResource: AssetResource) {
    data object OverSpeed : Sound(assetResource = MR.assets.sound_over_speed_mp3)

    data object StationaryCamera : Sound(assetResource = MR.assets.camera.stationary_camera_mp3)
    data object MobileCamera : Sound(assetResource = MR.assets.camera.mobile_camera_mp3)
    data object MediumSpeedCamera : Sound(assetResource = MR.assets.camera.medium_speed_camera_mp3)

    data object SpeedLimit40 : Sound(assetResource = MR.assets.limit.limit_40_mp3)
    data object SpeedLimit50 : Sound(assetResource = MR.assets.limit.limit_50_mp3)
    data object SpeedLimit60 : Sound(assetResource = MR.assets.limit.limit_60_mp3)
    data object SpeedLimit70 : Sound(assetResource = MR.assets.limit.limit_70_mp3)
    data object SpeedLimit80 : Sound(assetResource = MR.assets.limit.limit_80_mp3)
    data object SpeedLimit90 : Sound(assetResource = MR.assets.limit.limit_90_mp3)
    data object SpeedLimit100 : Sound(assetResource = MR.assets.limit.limit_100_mp3)
    data object SpeedLimit110 : Sound(assetResource = MR.assets.limit.limit_110_mp3)
    data object SpeedLimit120 : Sound(assetResource = MR.assets.limit.limit_120_mp3)

    data object TrafficPolice : Sound(assetResource = MR.assets.incident.traffic_police_mp3)
    data object CarCrash : Sound(assetResource = MR.assets.incident.car_crash_mp3)
    data object WildAnimals : Sound(assetResource = MR.assets.incident.wild_animals_mp3)
    data object TrafficJam : Sound(assetResource = MR.assets.incident.traffic_jam_mp3)
    data object RoadIncident : Sound(assetResource = MR.assets.incident.road_incident_mp3)

    data object TestAudioLevel : Sound(assetResource = MR.assets.test.test_audio_level_mp3)
}
