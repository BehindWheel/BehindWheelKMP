@file:Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
package com.egoriku.grodnoroads.coroutines

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class CStateFlow<T : Any>(private val stateFlow: StateFlow<T>) : CFlow<T>(stateFlow),
    StateFlow<T> by stateFlow

fun <T : Any> StateFlow<T>.asCStateFlow() = CStateFlow(this)

fun <T : Any> stateFlowOf(valueProvider: () -> T): CStateFlow<T> =
    MutableStateFlow(value = valueProvider()).asCStateFlow()