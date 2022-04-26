package com.egoriku.grodnoroads.data

import com.egoriku.grodnoroads.data.response.ReportActionResponse
import com.egoriku.grodnoroads.domain.repository.ReportActionRepository
import com.google.firebase.database.FirebaseDatabase
import com.kpstv.firebase.extensions.setValueAsync
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ReportActionRepositoryImpl(
    private val db: FirebaseDatabase
) : ReportActionRepository {

    override suspend fun report(
        reportActionResponse: ReportActionResponse
    ): Unit = withContext(Dispatchers.IO) {
        runCatching {
            val key = db.reference.child(USER_ACTIONS).push().key
                ?: throw IllegalArgumentException()

            db.reference
                .child(USER_ACTIONS)
                .child(key)
                .setValueAsync(reportActionResponse)
        }.getOrThrow()
    }
}