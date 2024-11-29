package com.egoriku.grodnoroads.guidance.data.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.guidance.domain.repository.UserCountRepository
import dev.gitlive.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal class UserCountRepositoryImpl(
    private val databaseReference: DatabaseReference
) : UserCountRepository {

    override fun loadAsFlow(): Flow<ResultOf.Success<Int>> = flowOf(
        ResultOf.Success(11)
    )
}
