package com.egoriku.grodnoroads.settings.changelog.data.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.extensions.coroutines.runCatchingCancellable
import com.egoriku.grodnoroads.settings.changelog.data.dto.ChangelogDTO
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogEntry
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform
import com.egoriku.grodnoroads.settings.changelog.domain.repository.ChangelogRepository
import com.egoriku.grodnoroads.shared.formatter.ChangelogFormatter
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

internal class ChangelogRepositoryImpl(
    private val firestore: FirebaseFirestore
) : ChangelogRepository {

    override suspend fun load(platform: ChangelogPlatform) = withContext(Dispatchers.IO) {
        runCatchingCancellable {
            val query = firestore
                .collection("whats_new")
                .where { "platform" equalTo platform.query }
                .orderBy("date", Direction.DESCENDING)
                .get()
                .documents.map { it.data<ChangelogDTO>() }

            ResultOf.Success(
                query.map {
                    ChangelogEntry(
                        versionName = it.name,
                        notes = it.notes,
                        releaseDate = ChangelogFormatter.format(
                            timestamp = it.releaseDate.seconds.seconds.inWholeMilliseconds
                        )
                    )
                }
            )
        }.getOrElse {
            ResultOf.Failure(it)
        }
    }
}
