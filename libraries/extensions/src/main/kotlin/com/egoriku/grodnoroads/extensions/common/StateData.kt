package com.egoriku.grodnoroads.extensions.common

sealed interface StateData<T> {
    object Idle : StateData<Nothing>
    data class Loaded<T>(val data: T) : StateData<T>
}