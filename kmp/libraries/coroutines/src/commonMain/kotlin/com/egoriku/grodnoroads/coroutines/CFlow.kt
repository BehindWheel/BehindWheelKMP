package com.egoriku.grodnoroads.coroutines

import kotlinx.coroutines.flow.Flow

open class CFlow<T : Any>(private val flow: Flow<T>) : Flow<T> by flow

fun <T : Any> Flow<T>.asCFlow() = CFlow(this)

fun <T : Any> flowOf(element: T): CFlow<T> = kotlinx.coroutines.flow.flowOf(element).asCFlow()