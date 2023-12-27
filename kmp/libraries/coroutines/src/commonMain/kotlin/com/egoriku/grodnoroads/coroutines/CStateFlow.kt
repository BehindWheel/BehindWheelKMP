@file:Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
package com.egoriku.grodnoroads.coroutines

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class CStateFlow<T>(private val stateFlow: StateFlow<T>) : CFlow<T>(stateFlow),
    StateFlow<T> by stateFlow

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
class CMutableStateFlow<T>(
    private val mutableStateFlow: MutableStateFlow<T>
) : CStateFlow<T>(mutableStateFlow), MutableStateFlow<T> by mutableStateFlow {
    constructor(initialValue: T) : this(MutableStateFlow(initialValue))
}

fun <T : Any> StateFlow<T>.asCStateFlow() = CStateFlow(this)

fun <T : Any> stateFlowOf(valueProvider: () -> T): CStateFlow<T> =
    MutableStateFlow(value = valueProvider()).asCStateFlow()