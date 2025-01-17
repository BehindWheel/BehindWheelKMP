package com.egoriku.grodnoroads.guidance.data.repository

import com.egoriku.grodnoroads.extensions.DateTime
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.guidance.data.mapper.ReportsMapper
import com.egoriku.grodnoroads.guidance.domain.repository.ReportsRepository
import com.egoriku.grodnoroads.shared.models.dto.ReportsDTO
import dev.gitlive.firebase.database.DatabaseReference
import kotlin.time.Duration.Companion.hours
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class ReportsRepositoryImpl(
    private val databaseReference: DatabaseReference
) : ReportsRepository {

    private val oneHourAgo = DateTime.currentTimeMillis() - 1.hours.inWholeMilliseconds

    override fun loadAsFlow() = databaseReference
        .child("reports")
        .orderByChild("timestamp")
        .startAt(oneHourAgo.toDouble())
        .valueEvents
        .map { snapshot ->
            val dtoList = snapshot.children.map { it.value<ReportsDTO>() }
            ResultOf.Success(ReportsMapper(dtoList))
        }
        .catch { ResultOf.Failure(it) }
        .flowOn(Dispatchers.IO)
}
