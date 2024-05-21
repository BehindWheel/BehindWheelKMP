package com.egoriku.grodnoroads.eventreporting.data.repository

import com.egoriku.grodnoroads.eventreporting.domain.repository.ReportingRepository
import com.egoriku.grodnoroads.shared.models.dto.MobileCameraDTO
import com.egoriku.grodnoroads.shared.models.dto.ReportsDTO
import dev.gitlive.firebase.database.DatabaseReference

internal class ReportingRepositoryImpl(
    private val databaseReference: DatabaseReference
) : ReportingRepository {

    override suspend fun reportEvent(reportsDTO: ReportsDTO) {
        databaseReference
            .child("reports")
            .push()
            .setValue(reportsDTO)
    }

    override suspend fun reportMobileCamera(mobileCameraDTO: MobileCameraDTO) {
        databaseReference
            .child("/v2/mobile_cameras/cameras")
            .push()
            .setValue(mobileCameraDTO)
    }
}