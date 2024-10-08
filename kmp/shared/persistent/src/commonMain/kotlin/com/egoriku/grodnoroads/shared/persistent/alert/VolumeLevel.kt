package com.egoriku.grodnoroads.shared.persistent.alert

enum class VolumeLevel(
    val levelName: String,
    val volumeLevel: Float,
    val loudness: Loudness
) {
    X03(levelName = "0.3x", volumeLevel = 0.3f, loudness = Loudness.DEFAULT),
    X06(levelName = "0.6x", volumeLevel = 0.6f, loudness = Loudness.DEFAULT),
    Default(levelName = "1.0x", volumeLevel = 1f, loudness = Loudness.DEFAULT),
    X12(levelName = "1.2x", volumeLevel = 1f, loudness = Loudness.NORMAL),
    X15(levelName = "1.5x", volumeLevel = 1f, loudness = Loudness.LOUDER)
}

enum class Loudness(val value: Int) {
    LOUDER(20),
    NORMAL(10),
    DEFAULT(0)
}
