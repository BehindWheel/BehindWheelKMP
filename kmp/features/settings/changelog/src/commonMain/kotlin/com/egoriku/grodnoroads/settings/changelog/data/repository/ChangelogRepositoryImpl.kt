package com.egoriku.grodnoroads.settings.changelog.data.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.extensions.coroutines.runCatchingCancellable
import com.egoriku.grodnoroads.settings.changelog.data.dto.ChangelogDTO
import com.egoriku.grodnoroads.settings.changelog.data.mapper.toDTO
import com.egoriku.grodnoroads.settings.changelog.data.mapper.toEntry
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform
import com.egoriku.grodnoroads.settings.changelog.domain.model.NewChangelogEntry
import com.egoriku.grodnoroads.settings.changelog.domain.repository.ChangelogRepository
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

private const val COLLECTION = "whats_new"

internal class ChangelogRepositoryImpl(
    private val firestore: FirebaseFirestore
) : ChangelogRepository {

    override suspend fun load(platform: ChangelogPlatform) = withContext(Dispatchers.IO) {
        runCatchingCancellable {
            val documents = firestore
                .collection(COLLECTION)
                .where { "platform" equalTo platform.query }
                .orderBy("date", Direction.DESCENDING)
                .get()
                .documents

            ResultOf.Success(
                documents.map { snapshot ->
                    snapshot.data<ChangelogDTO>().toEntry(id = snapshot.id, platform = platform)
                }
            )
        }.getOrElse {
            ResultOf.Failure(it)
        }
    }

    override suspend fun add(entry: NewChangelogEntry, platform: ChangelogPlatform) = withContext(Dispatchers.IO) {
        runCatchingCancellable {
            val reference = firestore.collection(COLLECTION).add(entry.toDTO(platform))
            val snapshot = reference.get()

            ResultOf.Success(snapshot.data<ChangelogDTO>().toEntry(id = reference.id, platform = platform))
        }.getOrElse {
            ResultOf.Failure(it)
        }
    }

    override suspend fun update(
        id: String,
        entry: NewChangelogEntry,
        platform: ChangelogPlatform
    ) = withContext(Dispatchers.IO) {
        runCatchingCancellable {
            val document = firestore.collection(COLLECTION).document(id)
            document.set(entry.toDTO(platform))
            val snapshot = document.get()

            ResultOf.Success(snapshot.data<ChangelogDTO>().toEntry(id = id, platform = platform))
        }.getOrElse {
            ResultOf.Failure(it)
        }
    }

    override suspend fun delete(id: String) = withContext(Dispatchers.IO) {
        runCatchingCancellable {
            firestore.collection(COLLECTION).document(id).delete()
            ResultOf.Success(Unit)
        }.getOrElse {
            ResultOf.Failure(it)
        }
    }
}
