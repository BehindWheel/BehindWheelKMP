package com.egoriku.grodnoroads.extension

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

typealias CancellationCallback = ((cause: Throwable) -> Unit)

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun DatabaseReference.singleValueEvent(onCancellation: CancellationCallback = {}): DataResponse<DataSnapshot> =
    suspendCancellableCoroutine { continuation ->
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                continuation.resume(DataResponse.error(error.toException()), onCancellation)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                continuation.resume(DataResponse.complete(snapshot), onCancellation)
            }
        }
        addListenerForSingleValueEvent(valueEventListener)
        continuation.invokeOnCancellation { removeEventListener(valueEventListener) }
    }

sealed class DataResponse<T> {
    data class Complete<T>(val data: T) : DataResponse<T>()
    data class Error<T>(val error: Exception) : DataResponse<T>()

    companion object {
        fun <T> complete(data: T): DataResponse<T> = Complete(data)
        fun <T> error(e: Exception): DataResponse<T> = Error(e)
    }
}

/**
 * A response state for ChildEvent listener.
 */
sealed class ChildEventResponse {
    data class Added(
        val snapshot: DataSnapshot,
        val previousChildName: String?
    ) : ChildEventResponse()

    data class Changed(
        val snapshot: DataSnapshot,
        val previousChildName: String?
    ) : ChildEventResponse()

    data class Moved(
        val snapshot: DataSnapshot,
        val previousChildName: String?
    ) : ChildEventResponse()

    data class Removed(val snapshot: DataSnapshot) : ChildEventResponse()
    data class Cancelled(val error: DatabaseError) : ChildEventResponse()
}

/**
 * A response state for ValueEvent listener.
 */
sealed class ValueEventResponse {
    data class Changed(val snapshot: DataSnapshot) : ValueEventResponse()
    data class Cancelled(val error: DatabaseError) : ValueEventResponse()
}