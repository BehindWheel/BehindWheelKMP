package com.egoriku.grodnoroads.guidance.data.repository

import com.egoriku.grodnoroads.extensions.DateTime
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.guidance.domain.repository.ReportsRepository
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.shared.models.MapEventType
import dev.gitlive.firebase.database.DatabaseReference
import kotlin.time.Duration.Companion.hours
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.flowOf

internal class ReportsRepositoryImpl(
    private val databaseReference: DatabaseReference
) : ReportsRepository {

    private val oneHourAgo = DateTime.currentTimeMillis() - 1.hours.inWholeMilliseconds

    override fun loadAsFlow() = flowOf(
        ResultOf.Success(
            listOf(
                Reports(
                    id = "",
                    timestamp = 17328660010000,
                    markerMessage = buildString {
                        append("(10:41) ")
                        append(MapEventType.TrafficJam.emoji)
                        append(" (на кольце Космонавтов)")
                    },
                    dialogTitle = "",
                    messages = persistentListOf(),
                    mapEventType = MapEventType.TrafficJam,
                    position = LatLng(53.674266, 23.861220)
                ),
                Reports(
                    id = "",
                    timestamp = 17328660010000,
                    markerMessage = buildString {
                        append("(11:05) ")
                        append(MapEventType.TrafficPolice.emoji)
                        append(" (остановка Друцк)")
                    },
                    dialogTitle = "",
                    messages = persistentListOf(),
                    mapEventType = MapEventType.TrafficPolice,
                    position = LatLng(53.670510, 23.862343)
                ),
                Reports(
                    id = "",
                    timestamp = 17328660010000,
                    markerMessage = buildString {
                        append("(10:27) ")
                        append(MapEventType.TrafficPolice.emoji)
                        append(" (ресторан Дом)")
                    },
                    dialogTitle = "",
                    messages = persistentListOf(),
                    mapEventType = MapEventType.TrafficPolice,
                    position = LatLng(53.656211, 23.801977)
                ),
                Reports(
                    id = "",
                    timestamp = 17328660010000,
                    markerMessage = buildString {
                        append("(11:09) ")
                        append(MapEventType.RoadIncident.emoji)
                        append(" (на Славинского)")
                    },
                    dialogTitle = "",
                    messages = persistentListOf(),
                    mapEventType = MapEventType.RoadIncident,
                    position = LatLng(53.646839, 23.829464)
                )
            )
        )
    )
}
