package com.egoriku.grodnoroads.data

import com.egoriku.grodnoroads.data.response.StationaryCameraResponse
import com.egoriku.grodnoroads.domain.repository.StationaryCameraRepository
import com.egoriku.grodnoroads.extension.DataResponse
import com.egoriku.grodnoroads.extension.singleValueEvent
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class StationaryCameraRepositoryImpl(
    private val db: FirebaseDatabase
) : StationaryCameraRepository {

    override suspend fun load() = withContext(Dispatchers.IO) {
        runCatching {
            val dataResponse = db.reference
                .child("stationary_camera")
                .singleValueEvent()

            when (dataResponse) {
                is DataResponse.Complete -> dataResponse.data.children.mapNotNull { it.getValue<StationaryCameraResponse>() }
                is DataResponse.Error -> emptyList()
            }
        }.getOrThrow()
    }
}