package com.egoriku.grodnoroads.data.response

import com.google.firebase.database.PropertyName

class ReportActionResponse(
    @PropertyName("type")
    @JvmField
    val type: String = "",

    @PropertyName("message")
    @JvmField
    val message: String = "",

    @PropertyName("position")
    @JvmField
    val position: LatLngResponse = LatLngResponse(),

    @PropertyName("timestamp")
    @JvmField
    val timestamp: Long = -1L
)