package com.egoriku.grodnoroads.screen.map.data

import com.egoriku.grodnoroads.extension.awaitValueEventListener
import com.egoriku.grodnoroads.extension.common.ResultOf
import com.egoriku.grodnoroads.screen.map.data.model.ReportsResponse
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class ReportsRepositoryImpl(databaseReference: DatabaseReference) : ReportsRepository {

    private val reportsReference = databaseReference.child("reports")

    override fun loadAsFlow() = reportsReference
        .orderByChild("timestamp")
        .awaitValueEventListener<ReportsResponse>()

    override suspend fun report(response: ReportsResponse) {
        reportsReference
            .push()
            .setValue(response)
            .await()
    }
}

interface ReportsRepository {

    fun loadAsFlow(): Flow<ResultOf<List<ReportsResponse>>>

    suspend fun report(response: ReportsResponse)
}