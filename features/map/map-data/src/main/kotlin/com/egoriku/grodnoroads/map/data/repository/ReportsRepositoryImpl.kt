package com.egoriku.grodnoroads.map.data.repository

import com.egoriku.grodnoroads.extensions.awaitValueEventListener
import com.egoriku.grodnoroads.extensions.common.ResultOf.Failure
import com.egoriku.grodnoroads.extensions.common.ResultOf.Success
import com.egoriku.grodnoroads.map.data.mapper.ReportsMapper
import com.egoriku.grodnoroads.map.domain.repository.ReportsRepository
import com.egoriku.grodnoroads.shared.core.models.dto.ReportsDTO
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class ReportsRepositoryImpl(
    private val databaseReference: DatabaseReference
) : ReportsRepository {

    override fun loadAsFlow(startAt: Long) = databaseReference
        .child("reports")
        .orderByChild("timestamp")
        .startAt(startAt.toDouble())
        .awaitValueEventListener<ReportsDTO>()
        .map { resultOf ->
            when (resultOf) {
                is Failure -> Failure(resultOf.exception)
                is Success -> Success(ReportsMapper(resultOf.value))
            }
        }
        .flowOn(Dispatchers.IO)
}