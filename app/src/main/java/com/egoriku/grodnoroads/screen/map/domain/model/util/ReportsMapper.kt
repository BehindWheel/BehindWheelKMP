package com.egoriku.grodnoroads.screen.map.domain.model.util

import com.egoriku.grodnoroads.extension.appendIfNotEmpty
import com.egoriku.grodnoroads.extension.distanceTo
import com.egoriku.grodnoroads.screen.map.data.model.ReportsResponse
import com.egoriku.grodnoroads.screen.map.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.screen.map.domain.model.MapEventType
import com.egoriku.grodnoroads.screen.map.domain.model.MapEventType.CarCrash
import com.egoriku.grodnoroads.screen.map.domain.model.MapEventType.RoadIncident
import com.egoriku.grodnoroads.screen.map.domain.model.MapEventType.TrafficJam
import com.egoriku.grodnoroads.screen.map.domain.model.MapEventType.TrafficPolice
import com.egoriku.grodnoroads.screen.map.domain.model.MapEventType.WildAnimals
import com.egoriku.grodnoroads.screen.map.domain.model.MessageItem
import com.egoriku.grodnoroads.screen.map.domain.model.Source
import com.egoriku.grodnoroads.util.DateUtil
import com.google.android.gms.maps.model.LatLng

private const val MERGE_ALERT_DISTANCE = 200

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

            distance < MERGE_ALERT_DISTANCE
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
                position = item.position,
                dialogTitle = buildDialogTitle(data),
                markerMessage = buildMarkerShortMessage(data)
            )
        } else {
            val action = Reports(
                messages = listOf(
                    MessageItem(
                        message = "(${DateUtil.formatToTime(data.timestamp)}) ${data.message}",
                        source = Source.sourceFromString(data.source)
                    )
                ),
                markerMessage = buildMarkerShortMessage(data),
                dialogTitle = buildDialogTitle(data),
                position = LatLng(data.latitude, data.longitude),
                mapEventType = MapEventType.eventFromString(data.type)
            )

            mergedReports.add(action)
        }
    }
    return mergedReports
}

private fun buildMarkerShortMessage(data: ReportsResponse) =
    when (MapEventType.eventFromString(data.type)) {
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

private fun buildDialogTitle(data: ReportsResponse) =
    when (MapEventType.eventFromString(data.type)) {
        TrafficPolice -> buildString {
            append(TrafficPolice.emoji)
            appendIfNotEmpty(data.shortMessage, " ${data.shortMessage}")
        }
        RoadIncident -> buildString {
            append(RoadIncident.emoji)
            appendIfNotEmpty(data.shortMessage, " ${data.shortMessage}")
        }
        WildAnimals -> buildString {
            append(WildAnimals.emoji)
            appendIfNotEmpty(data.shortMessage, " ${data.shortMessage}")
        }
        CarCrash -> buildString {
            append(CarCrash.emoji)
            appendIfNotEmpty(data.shortMessage, " ${data.shortMessage}")
        }
        TrafficJam -> buildString {
            append(TrafficJam.emoji)
            appendIfNotEmpty(data.shortMessage, " ${data.shortMessage}")
        }
        else -> data.shortMessage
    }