package com.egoriku.grodnoroads.extension

import com.egoriku.grodnoroads.extension.common.ResultOf
import com.google.firebase.FirebaseException
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

inline fun <reified T> DatabaseReference.awaitValueEventListener(): Flow<ResultOf<List<T>>> =
    callbackFlow {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val entityList = snapshot.children.mapNotNull { dataSnapshot ->
                        dataSnapshot.getValue<T>()
                    }

                    trySend(ResultOf.Success(entityList))
                } catch (e: DatabaseException) {
                    trySend(ResultOf.Failure(e))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                val exception = FirebaseException(getErrorMessage(error.code))
                trySend(ResultOf.Failure(exception))
            }
        }

        addValueEventListener(valueEventListener)
        awaitClose {
            removeEventListener(valueEventListener)
        }
    }

fun getErrorMessage(errorCode: Int): String {
    return when (errorCode) {
        DatabaseError.DISCONNECTED -> "disconnected"
        DatabaseError.EXPIRED_TOKEN -> "expired.token"
        DatabaseError.INVALID_TOKEN -> "invalid.token"
        DatabaseError.MAX_RETRIES -> "max.retries"
        DatabaseError.NETWORK_ERROR -> "network.error"
        DatabaseError.OPERATION_FAILED -> "operation.failed"
        DatabaseError.OVERRIDDEN_BY_SET -> "overridden.by.set"
        DatabaseError.PERMISSION_DENIED -> "permission.denied"
        DatabaseError.UNAVAILABLE -> "unavailable"
        DatabaseError.USER_CODE_EXCEPTION -> "user.code.exception"
        DatabaseError.UNKNOWN_ERROR -> "unknown.error"
        else -> "other error"
    }
}