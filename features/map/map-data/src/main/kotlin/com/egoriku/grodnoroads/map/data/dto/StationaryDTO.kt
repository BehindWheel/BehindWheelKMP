package com.egoriku.grodnoroads.map.data.dto

import com.google.firebase.database.PropertyName

internal class StationaryDTO(
    @PropertyName("message")
    @JvmField
    val message: String = "",

    @PropertyName("speed")
    @JvmField
    val speed: Int = 0,

    @PropertyName("latitude")
    @JvmField
    val latitude: Double = 0.0,

    @PropertyName("longitude")
    @JvmField
    val longitude: Double = 0.0,

    @PropertyName("last_update_time")
    @JvmField
    val lastUpdateTime: Long = 0L
)