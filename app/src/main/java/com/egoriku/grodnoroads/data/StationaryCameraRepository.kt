package com.egoriku.grodnoroads.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

class StationaryCameraRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun load(): List<StationaryEntity> {
        return db.collection("stationary_camera").get().await().toObjects()
    }
}