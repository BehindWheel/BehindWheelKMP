package com.egoriku.grodnoroads.screen.map.data.model

import com.google.firebase.database.PropertyName

class ReportsResponse(
    @PropertyName("type")
    @JvmField
    val type: String = "",

    @PropertyName("short_message")
    @JvmField
    val shortMessage: String = "",

    @PropertyName("message")
    @JvmField
    val message: String = "",

    @PropertyName("latitude")
    @JvmField
    val latitude: Double = 0.0,

    @PropertyName("longitude")
    @JvmField
    val longitude: Double = 0.0,

    @PropertyName("timestamp")
    @JvmField
    val timestamp: Long = -1,

    @PropertyName("source")
    @JvmField
    val source: String = ""
)