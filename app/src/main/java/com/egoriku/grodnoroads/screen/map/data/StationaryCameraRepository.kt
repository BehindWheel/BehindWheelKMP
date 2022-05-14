package com.egoriku.grodnoroads.screen.map.data

import com.egoriku.grodnoroads.extension.awaitSingleValueEventListener
import com.egoriku.grodnoroads.extension.common.ResultOf
import com.egoriku.grodnoroads.screen.map.data.model.StationaryResponse
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow

internal class StationaryCameraRepositoryImpl(
    private val databaseReference: DatabaseReference
) : StationaryCameraRepository {

    override suspend fun loadAsFlow() = databaseReference
        .child("stationary_camera")
        .awaitSingleValueEventListener<StationaryResponse>()
}

interface StationaryCameraRepository {

    suspend fun loadAsFlow(): Flow<ResultOf<List<StationaryResponse>>>
}