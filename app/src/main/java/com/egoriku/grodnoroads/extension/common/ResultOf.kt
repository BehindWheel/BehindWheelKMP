package com.egoriku.grodnoroads.extension.common

sealed class ResultOf<out T> {
    data class Success<out T>(val value: T) : ResultOf<T>()
    data class Failure(val exception: Exception) : ResultOf<Nothing>()
}
