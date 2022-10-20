package com.egoriku.grodnoroads.extensions.common

import androidx.compose.runtime.Stable

@Stable
data class StableList<T>(private val delegate: List<T>) : List<T> by delegate

fun <T> List<T>.asStableList() = StableList<T>(this)