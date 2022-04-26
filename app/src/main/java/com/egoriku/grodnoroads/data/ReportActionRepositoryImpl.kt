package com.egoriku.grodnoroads.data

import com.egoriku.grodnoroads.data.response.ReportActionResponse
import com.egoriku.grodnoroads.domain.repository.ReportActionRepository
import com.egoriku.grodnoroads.util.valueEventFlow
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.kpstv.firebase.ValueEventResponse
import com.kpstv.firebase.extensions.setValueAsync
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun usersActions(): Flow<List<ReportActionResponse>> {
        return db.reference.child(USER_ACTIONS).valueEventFlow().map { valueEventResponse ->
            when (valueEventResponse) {
                is ValueEventResponse.Cancelled -> emptyList()
                is ValueEventResponse.Changed -> valueEventResponse.snapshot.children.mapNotNull {
                    it.getValue<ReportActionResponse>()
                }
            }
        }
    }
}