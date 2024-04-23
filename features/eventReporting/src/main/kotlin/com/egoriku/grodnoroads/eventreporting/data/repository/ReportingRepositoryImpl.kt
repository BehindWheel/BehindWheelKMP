package com.egoriku.grodnoroads.eventreporting.data.repository

import com.egoriku.grodnoroads.eventreporting.domain.repository.ReportingRepository
import com.egoriku.grodnoroads.shared.core.models.dto.MobileCameraDTO
import com.egoriku.grodnoroads.shared.core.models.dto.ReportsDTO
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await

internal class ReportingRepositoryImpl(
    private val databaseReference: DatabaseReference
) : ReportingRepository {

    override suspend fun reportEvent(reportsDTO: ReportsDTO) {
        databaseReference
            .child("reports")
            .push()
            .setValue(reportsDTO)
            .await()
    }

    override suspend fun reportMobileCamera(mobileCameraDTO: MobileCameraDTO) {
        databaseReference
            .child("/v2/mobile_cameras/cameras")
            .push()
            .setValue(mobileCameraDTO)
            .await()
    }
}