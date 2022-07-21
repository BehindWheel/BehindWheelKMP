package com.egoriku.grodnoroads.screen.settings.faq.data

import com.egoriku.grodnoroads.extension.await
import com.egoriku.grodnoroads.extension.common.ResultOf
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class FaqRepositoryImpl(
    private val firestore: FirebaseFirestore
) : FaqRepository {

    override suspend fun load() = withContext(Dispatchers.IO) {
        firestore
            .collection("faq")
            .orderBy("id")
            .await<FaqResponse>()
    }
}

interface FaqRepository {

    suspend fun load(): ResultOf<List<FaqResponse>>
}