package com.egoriku.grodnoroads.extensions

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.google.firebase.firestore.Query
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend inline fun <reified T> Query.await(): ResultOf<List<T>> =
    suspendCancellableCoroutine { continuation ->
        get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                try {
                    val data: List<T> = task.result?.toObjects(T::class.java).orEmpty()
                    continuation.resume(ResultOf.Success(data))
                } catch (exception: Exception) {
                    continuation.resume(ResultOf.Failure(exception))
                }
            } else {
                val exception = task.exception

                if (exception != null) {
                    continuation.resume(ResultOf.Failure(exception))
                }
            }
        }
    }
