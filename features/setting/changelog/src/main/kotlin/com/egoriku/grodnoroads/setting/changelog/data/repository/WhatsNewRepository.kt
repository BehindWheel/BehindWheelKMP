package com.egoriku.grodnoroads.setting.changelog.data.repository

import com.egoriku.grodnoroads.extensions.await
import com.egoriku.grodnoroads.extensions.common.ResultOf.Failure
import com.egoriku.grodnoroads.extensions.common.ResultOf.Success
import com.egoriku.grodnoroads.setting.changelog.data.dto.ChangelogDTO
import com.egoriku.grodnoroads.setting.changelog.domain.model.ReleaseNotes
import com.egoriku.grodnoroads.setting.changelog.domain.repository.ChangelogRepository
import com.egoriku.grodnoroads.setting.changelog.domain.util.ddMMMyyyy
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ChangelogRepositoryImpl(
    private val firestore: FirebaseFirestore
) : ChangelogRepository {

    override suspend fun load() = withContext(Dispatchers.IO) {
        val whatsNewResponse = firestore
            .collection("whats_new")
            .orderBy("code", Query.Direction.DESCENDING)
            .await<ChangelogDTO>()

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

