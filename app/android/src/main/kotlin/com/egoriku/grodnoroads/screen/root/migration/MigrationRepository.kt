package com.egoriku.grodnoroads.screen.root.migration

import com.egoriku.grodnoroads.extensions.awaitValueEventListener
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.screen.root.store.RootStoreFactory.MigrationModel
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MigrationRepository(private val databaseReference: DatabaseReference) {

    fun loadAsFlow(): Flow<ResultOf<MigrationModel>> = databaseReference
        .child("migration")
        .awaitValueEventListener<MigrationDTO>()
        .map { resultOf ->
            when (resultOf) {
                is ResultOf.Failure -> {
                    ResultOf.Failure(resultOf.exception)
                }
                is ResultOf.Success -> {
                    val dto = resultOf.value.firstOrNull() ?: MigrationDTO()

                    ResultOf.Success(
                        MigrationModel(
                            enabled = dto.enabled,
                            link = dto.link,
                            newPackage = dto.newPackage
                        )
                    )
                }
            }
        }
        .flowOn(Dispatchers.IO)
}