package com.egoriku.grodnoroads.setting.whatsnew.data.repository

import com.egoriku.grodnoroads.extensions.await
import com.egoriku.grodnoroads.extensions.common.ResultOf.Failure
import com.egoriku.grodnoroads.extensions.common.ResultOf.Success
import com.egoriku.grodnoroads.setting.whatsnew.data.dto.WhatsNewDTO
import com.egoriku.grodnoroads.setting.whatsnew.domain.model.ReleaseNotes
import com.egoriku.grodnoroads.setting.whatsnew.domain.repository.WhatsNewRepository
import com.egoriku.grodnoroads.setting.whatsnew.domain.util.ddMMMyyyy
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class WhatsNewRepositoryImpl(
    private val firestore: FirebaseFirestore
) : WhatsNewRepository {

    override suspend fun load() = withContext(Dispatchers.IO) {
        val whatsNewResponse = firestore
            .collection("whats_new")
            .orderBy("code", Query.Direction.DESCENDING)
            .await<WhatsNewDTO>()

        return@withContext when (whatsNewResponse) {
            is Failure -> Failure(whatsNewResponse.exception)
            is Success -> Success(whatsNewResponse.value.map {
                ReleaseNotes(
                    versionCode = it.code,
                    versionName = it.name,
                    notes = it.notes.replace("\\n", "\n"),
                    releaseDate = it.releaseDate.ddMMMyyyy
                )
            })
        }
    }
}

