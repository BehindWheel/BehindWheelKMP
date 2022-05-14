package com.egoriku.grodnoroads.screen.map.data

import com.egoriku.grodnoroads.extension.awaitValueEventListener
import com.egoriku.grodnoroads.extension.common.ResultOf
import com.egoriku.grodnoroads.screen.map.data.model.MobileCameraResponse
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow

internal class MobileCameraRepositoryImpl(
    private val databaseReference: DatabaseReference
) : MobileCameraRepository {

    override suspend fun loadAsFlow() = databaseReference
        .child("temporary_camera/cameras")
        .awaitValueEventListener<MobileCameraResponse>()
}

interface MobileCameraRepository {

    suspend fun loadAsFlow(): Flow<ResultOf<List<MobileCameraResponse>>>
}