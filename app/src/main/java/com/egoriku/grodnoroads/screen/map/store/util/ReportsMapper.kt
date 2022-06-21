package com.egoriku.grodnoroads.screen.map.store.util

import com.egoriku.grodnoroads.extension.appendIfNotEmpty
import com.egoriku.grodnoroads.extension.distanceTo
import com.egoriku.grodnoroads.screen.map.data.model.ReportsResponse
import com.egoriku.grodnoroads.screen.map.domain.MapEvent.Reports
import com.egoriku.grodnoroads.screen.map.domain.MapEventType
import com.egoriku.grodnoroads.screen.map.domain.MapEventType.*
import com.egoriku.grodnoroads.screen.map.domain.MessageItem
import com.egoriku.grodnoroads.screen.map.domain.Source
import com.egoriku.grodnoroads.util.DateUtil
import com.google.android.gms.maps.model.LatLng

fun List<ReportsResponse>.mapAndMerge(): List<Reports> {
    return groupBy { it.type }
        .mapValues { it.value.mergeReports() }
        .values
        .flatten()
}

private fun List<ReportsResponse>.mergeReports(): List<Reports> {
    val mergedReports = mutableListOf<Reports>()

    forEach { data ->
        val index = mergedReports.indexOfFirst { calcAction ->
            val distance = calcAction.position distanceTo LatLng(data.latitude, data.longitude)

            distance < 100
        }

        if (index != -1) {
            val item: Reports = mergedReports[index]

            mergedReports[index] = item.copy(
                messages = item.messages.toMutableList().apply {
                    add(
                        MessageItem(
                            message = "(${DateUtil.formatToTime(data.timestamp)}) ${data.message}",
                            source = Source.sourceFromString(data.source)
                        )
                    )
                },
                position = item.position
            )
        } else {
            val eventType = MapEventType.eventFromString(data.type)
            val shortMessage = when (eventType) {
                TrafficPolice -> buildString {
                    append("(${DateUtil.formatToTime(data.timestamp)}) ")
                    append(TrafficPolice.emoji)
                    appendIfNotEmpty(data.shortMessage, " (${data.shortMessage})")
                }
                RoadIncident -> buildString {
                    append("(${DateUtil.formatToTime(data.timestamp)}) ")
                    append(RoadIncident.emoji)
                    appendIfNotEmpty(data.shortMessage, " (${data.shortMessage})")
                }
                WildAnimals -> buildString {
                    append("(${DateUtil.formatToTime(data.timestamp)}) ")
                    append(WildAnimals.emoji)
                    appendIfNotEmpty(data.shortMessage, " (${data.shortMessage})")
                }
                CarCrash -> buildString {
                    append("(${DateUtil.formatToTime(data.timestamp)}) ")
                    append(CarCrash.emoji)
                    appendIfNotEmpty(data.shortMessage, " (${data.shortMessage})")
                }
                TrafficJam -> buildString {
                    append("(${DateUtil.formatToTime(data.timestamp)}) ")
                    append(TrafficJam.emoji)
                    appendIfNotEmpty(data.shortMessage, " (${data.shortMessage})")
                }
                else -> data.shortMessage
            }

            val action = Reports(
                messages = listOf(
                    MessageItem(
                        message = "(${DateUtil.formatToTime(data.timestamp)}) ${data.message}",
                        source = Source.sourceFromString(data.source)
                    )
                ),
                shortMessage = shortMessage,
                position = LatLng(data.latitude, data.longitude),
                mapEventType = eventType
            )

            mergedReports.add(action)
        }
    }
    return mergedReports
}