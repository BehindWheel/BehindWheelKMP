package com.egoriku.grodnoroads.data

import com.egoriku.grodnoroads.domain.repository.StationaryCameraRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

internal class StationaryCameraRepositoryImpl(
    private val db: FirebaseFirestore
) : StationaryCameraRepository {

    override suspend fun load() = withContext(Dispatchers.IO) {
        runCatching {
            db.collection("stationary_camera").get().await().toObjects<StationaryEntity>()
        }.getOrDefault(emptyList())
    }
}