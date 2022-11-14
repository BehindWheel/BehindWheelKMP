package com.egoriku.grodnoroads.extensions.common

sealed interface StateData<out T> {
    object Idle : StateData<Nothing>
    data class Loaded<out T>(val data: T) : StateData<T>
}