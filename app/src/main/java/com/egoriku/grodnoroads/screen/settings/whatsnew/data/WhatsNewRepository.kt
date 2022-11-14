package com.egoriku.grodnoroads.screen.settings.whatsnew.data

import com.egoriku.grodnoroads.extensions.await
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class WhatsNewRepositoryImpl(
    private val firestore: FirebaseFirestore
) : WhatsNewRepository {

    override suspend fun load() = withContext(Dispatchers.IO) {
        firestore
            .collection("whats_new")
            .orderBy("id", Query.Direction.DESCENDING)
            .await<WhatsNewResponse>()
    }
}

interface WhatsNewRepository {

    suspend fun load(): ResultOf<List<WhatsNewResponse>>
}