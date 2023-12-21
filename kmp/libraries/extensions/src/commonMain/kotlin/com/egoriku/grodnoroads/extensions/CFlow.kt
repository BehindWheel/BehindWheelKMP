package com.egoriku.grodnoroads.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// TODO: Extract to coroutines module
open class CFlow<T : Any>(private val flow: Flow<T>) : Flow<T> by flow
fun <T : Any> Flow<T>.asCFlow() = CFlow(this)

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
open class CStateFlow<T : Any>(private val stateFlow: StateFlow<T>) : CFlow<T>(stateFlow),
    StateFlow<T> by stateFlow

fun <T : Any> StateFlow<T>.asCStateFlow() = CStateFlow(this)

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
class CMutableStateFlow<T : Any>(
    private val mutableStateFlow: MutableStateFlow<T>
) : CStateFlow<T>(mutableStateFlow), MutableStateFlow<T> by mutableStateFlow {
    constructor(initialValue: T) : this(MutableStateFlow(initialValue))
}

fun <T : Any> stateFlowOf(valueProvider: () -> T): CStateFlow<T> =
    MutableStateFlow(value = valueProvider()).asCStateFlow()