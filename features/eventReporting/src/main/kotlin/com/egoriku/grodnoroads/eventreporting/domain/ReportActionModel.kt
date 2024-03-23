package com.egoriku.grodnoroads.eventreporting.domain

data class ReportActionModel(
    val type: String,
    val shortMessage: String,
    val message: String,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val source: String,
)