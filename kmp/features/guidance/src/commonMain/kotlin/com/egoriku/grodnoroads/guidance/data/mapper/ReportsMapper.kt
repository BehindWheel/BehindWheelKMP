package com.egoriku.grodnoroads.guidance.data.mapper

import com.egoriku.grodnoroads.extensions.DateTime
import com.egoriku.grodnoroads.extensions.appendIfNotEmpty
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.guidance.domain.model.MessageItem
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.location.calc.roundDistanceTo
import com.egoriku.grodnoroads.shared.models.MapEventType
import com.egoriku.grodnoroads.shared.models.MessageSource
import com.egoriku.grodnoroads.shared.models.dto.ReportsDTO

private const val MERGE_ALERT_DISTANCE = 200

internal object ReportsMapper : (List<ReportsDTO>) -> List<Reports> {

    override fun invoke(reportsDTO: List<ReportsDTO>): List<Reports> {
        return reportsDTO.groupBy { it.type }
            .mapValues { it.value.mergeReports() }
            .values
            .flatten()
    }

    private fun List<ReportsDTO>.mergeReports(): List<Reports> {
        val mergedReports = mutableListOf<Reports>()

        forEach { data ->
            val position = LatLng(data.latitude, data.longitude)
            val eventType = MapEventType.eventFromString(data.type)
            val shortMessage = data.shortMessage.trim()

            val index = mergedReports.indexOfFirst { calcAction ->
                calcAction.position roundDistanceTo position < MERGE_ALERT_DISTANCE
            }

            if (index != -1) {
                val item = mergedReports[index]

                mergedReports[index] = item.copy(
                    messages = item.messages + buildMessageItem(data),
                    position = position,
                    dialogTitle = buildDialogTitle(eventType, shortMessage),
                    markerMessage = buildMarkerShortMessage(data, eventType, shortMessage),
                    timestamp = data.timestamp
                )
            } else {
                mergedReports += Reports(
                    id = "${data.type}-${data.latitude}-${data.longitude}",
                    messages = listOf(buildMessageItem(data)),
                    markerMessage = buildMarkerShortMessage(data, eventType, shortMessage),
                    dialogTitle = buildDialogTitle(eventType, shortMessage),
                    shortMessage = shortMessage,
                    position = position,
                    mapEventType = eventType,
                    timestamp = data.timestamp
                )
            }
        }
        return mergedReports
    }

    private fun buildMessageItem(data: ReportsDTO) = MessageItem(
        message = "(${DateTime.formatToTime(data.timestamp)}) ${data.message.emojiFix()}",
        messageSource = MessageSource.sourceFromString(data.source)
    )

    private fun buildMarkerShortMessage(
        data: ReportsDTO,
        eventType: MapEventType,
        shortMessage: String
    ): String = when (eventType) {
        MapEventType.Unsupported -> shortMessage
        else -> buildString {
            append("(${DateTime.formatToTime(data.timestamp)}) ")
            append(eventType.emoji)
            appendIfNotEmpty(shortMessage, " ($shortMessage)")
        }
    }

    private fun buildDialogTitle(
        eventType: MapEventType,
        shortMessage: String
    ): String = when (eventType) {
        MapEventType.Unsupported -> shortMessage
        else -> buildString {
            append(eventType.emoji)
            appendIfNotEmpty(shortMessage, " $shortMessage")
        }
    }

    private fun String.emojiFix() = replace("(policecar)", "\uD83D\uDE93")
}
