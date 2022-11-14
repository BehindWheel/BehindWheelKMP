package com.egoriku.grodnoroads.map.data.repository

import com.egoriku.grodnoroads.extensions.awaitValueEventListener
import com.egoriku.grodnoroads.extensions.common.ResultOf.Failure
import com.egoriku.grodnoroads.extensions.common.ResultOf.Success
import com.egoriku.grodnoroads.map.data.dto.ReportsDTO
import com.egoriku.grodnoroads.map.data.mapper.ReportsActionMapper
import com.egoriku.grodnoroads.map.data.mapper.ReportsMapper
import com.egoriku.grodnoroads.map.domain.model.report.ReportActionModel
import com.egoriku.grodnoroads.map.domain.repository.ReportsRepository
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

internal class ReportsRepositoryImpl(databaseReference: DatabaseReference) : ReportsRepository {

    private val reportsReference = databaseReference.child("reports")

    override fun loadAsFlow() = reportsReference
        .orderByChild("timestamp")
        .awaitValueEventListener<ReportsDTO>()
        .map { resultOf ->
            when (resultOf) {
                is Failure -> Failure(resultOf.exception)
                is Success -> Success(ReportsMapper(resultOf.value))
            }
        }
        .flowOn(Dispatchers.IO)

    override suspend fun report(actionModel: ReportActionModel) {
        reportsReference
            .push()
            .setValue(ReportsActionMapper(actionModel))
            .await()
    }
}