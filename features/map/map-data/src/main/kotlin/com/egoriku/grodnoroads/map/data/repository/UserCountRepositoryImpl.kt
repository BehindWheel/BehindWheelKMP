package com.egoriku.grodnoroads.map.data.repository

import com.egoriku.grodnoroads.extensions.awaitValueEventListener
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.map.domain.repository.UserCountRepository
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class UserCountRepositoryImpl(
    private val databaseReference: DatabaseReference
) : UserCountRepository {

    override fun loadAsFlow() = databaseReference
        .child("users_count")
        .awaitValueEventListener<Int>()
        .map { resultOf ->
            when (resultOf) {
                is ResultOf.Failure -> ResultOf.Failure(resultOf.throwable)
                is ResultOf.Success -> ResultOf.Success(resultOf.value.firstOrNull() ?: 0)
            }
        }
        .flowOn(Dispatchers.IO)
}