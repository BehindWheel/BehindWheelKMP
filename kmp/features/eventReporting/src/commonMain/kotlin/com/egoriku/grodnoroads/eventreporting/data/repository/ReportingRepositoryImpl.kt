package com.egoriku.grodnoroads.eventreporting.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.egoriku.grodnoroads.datastore.edit
import com.egoriku.grodnoroads.eventreporting.domain.repository.ReportingRepository
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.shared.models.dto.MobileCameraDTO
import com.egoriku.grodnoroads.shared.models.dto.ReportsDTO
import com.egoriku.grodnoroads.shared.persistent.reporting.lastReportTime
import com.egoriku.grodnoroads.shared.persistent.reporting.reportsInLastHour
import com.egoriku.grodnoroads.shared.persistent.reporting.updateLastReportTime
import com.egoriku.grodnoroads.shared.persistent.reporting.updateReportsInLastHour
import dev.gitlive.firebase.database.DatabaseReference
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.first

private const val MAX_REPORTS_PER_HOUR = 6
private val reportTimeout = 30.seconds.inWholeMilliseconds

@OptIn(ExperimentalTime::class)
internal class ReportingRepositoryImpl(
    private val databaseReference: DatabaseReference,
    private val dataStore: DataStore<Preferences>
) : ReportingRepository {

    override suspend fun reportEvent(reportsDTO: ReportsDTO): ResultOf<Unit> {
        return reportData("reports", reportsDTO)
    }

    override suspend fun reportMobileCamera(mobileCameraDTO: MobileCameraDTO): ResultOf<Unit> {
        return reportData("/v2/mobile_cameras/cameras", mobileCameraDTO)
    }

    private suspend fun <T> reportData(path: String, data: T): ResultOf<Unit> {
        return runCatching {
            checkAndUpdateRateLimit()
            databaseReference
                .child(path)
                .push()
                .setValue(data)
        }.fold(
            onSuccess = { ResultOf.successOf(Unit) },
            onFailure = { ResultOf.failureOf(it) }
        )
    }

    private suspend fun checkAndUpdateRateLimit() {
        val currentTimestamp = Clock.System.now().toEpochMilliseconds()
        val preferences = dataStore.data.first()
        val lastReportTime = preferences.lastReportTime
        val reportsInLastHour = when {
            currentTimestamp - lastReportTime > 1.hours.inWholeMilliseconds -> {
                dataStore.edit { updateReportsInLastHour(0) }
                0
            }
            else -> {
                preferences.reportsInLastHour
            }
        }

        if (reportsInLastHour >= MAX_REPORTS_PER_HOUR) {
            throw RateLimitExceededException("User has exceeded the maximum of $MAX_REPORTS_PER_HOUR reports per hour.")
        }

        val timeSinceLastReport = currentTimestamp - lastReportTime
        if (timeSinceLastReport >= reportTimeout) {
            dataStore.edit {
                updateLastReportTime(currentTimestamp)
                updateReportsInLastHour(reportsInLastHour + 1)
            }
        } else {
            val waitTime = (reportTimeout - timeSinceLastReport) / 1000
            throw RateLimitExceededException("User banned for $waitTime seconds")
        }
    }
}

internal class RateLimitExceededException(message: String) : Exception(message)
