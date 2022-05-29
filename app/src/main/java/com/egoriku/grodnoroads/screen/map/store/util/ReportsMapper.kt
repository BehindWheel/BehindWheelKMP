package com.egoriku.grodnoroads.screen.map.store.util

import com.egoriku.grodnoroads.domain.model.MapEventType
import com.egoriku.grodnoroads.domain.model.Source
import com.egoriku.grodnoroads.extension.appendIfNotEmpty
import com.egoriku.grodnoroads.extension.distanceTo
import com.egoriku.grodnoroads.screen.map.MapComponent.MapEvent.UserActions
import com.egoriku.grodnoroads.screen.map.MapComponent.MessageItem
import com.egoriku.grodnoroads.screen.map.data.model.ReportsResponse
import com.egoriku.grodnoroads.util.DateUtil
import com.google.android.gms.maps.model.LatLng

fun List<ReportsResponse>.mapAndMerge(): List<UserActions> {
    return groupBy { it.type }
        .mapValues { it.value.mergeReports() }
        .values
        .flatten()
}

private fun List<ReportsResponse>.mergeReports(): List<UserActions> {
    val mergedReports = mutableListOf<UserActions>()

    forEach { data ->
        val index = mergedReports.indexOfFirst { calcAction ->
            val distance = calcAction.position distanceTo LatLng(data.latitude, data.longitude)

            distance < 100
        }

        if (index != -1) {
            val item: UserActions = mergedReports[index]

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
                MapEventType.TrafficPolice -> buildString {
                    append("(${DateUtil.formatToTime(data.timestamp)}) ")
                    append("\uD83D\uDC6E")
                    appendIfNotEmpty(data.shortMessage, " (${data.shortMessage})")
                }
                MapEventType.RoadAccident -> buildString {
                    append("(${DateUtil.formatToTime(data.timestamp)}) ")
                    append("\uD83D\uDCA5")
                    appendIfNotEmpty(data.shortMessage, " (${data.shortMessage})")
                }
                else -> data.shortMessage
            }

            val action = UserActions(
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