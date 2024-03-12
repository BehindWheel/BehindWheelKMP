package com.egoriku.grodnoroads.eventreporting.data.repository

import com.egoriku.grodnoroads.eventreporting.data.mapper.ReportsActionMapper
import com.egoriku.grodnoroads.eventreporting.domain.ReportActionModel
import com.egoriku.grodnoroads.eventreporting.domain.repository.ReportingRepository
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await

internal class ReportingRepositoryImpl(
    private val databaseReference: DatabaseReference
) : ReportingRepository {

    override suspend fun report(actionModel: ReportActionModel) {
        databaseReference
            .child("reports")
            .push()
            .setValue(ReportsActionMapper(actionModel))
            .await()
    }
}