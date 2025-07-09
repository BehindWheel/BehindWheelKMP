package com.egoriku.grodnoroads.extensions.common

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

sealed class ResultOf<out T> {
    data class Success<out T>(val value: T) : ResultOf<T>()
    data class Failure(val throwable: Throwable) : ResultOf<Nothing>()

    companion object {
        fun <T> successOf(value: T): ResultOf<T> = Success(value)
        fun failureOf(throwable: Throwable): ResultOf<Nothing> = Failure(throwable)

        @OptIn(ExperimentalContracts::class)
        inline fun <reified R, reified T> ResultOf<T>.fold(
            onSuccess: (value: T) -> R,
            onFailure: (exception: Throwable) -> R
        ): R {
            contract {
                callsInPlace(onSuccess, InvocationKind.AT_MOST_ONCE)
                callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
            }
            return when (this) {
                is Failure -> onFailure(throwable)
                is Success -> onSuccess(value)
            }
        }
    }
}
