package com.egoriku.grodnoroads.common

sealed interface Condition<out T> {

    object Loading : Condition<Nothing>
    object Error : Condition<Nothing>
    data class Loaded<out T>(val data: T) : Condition<T>
}