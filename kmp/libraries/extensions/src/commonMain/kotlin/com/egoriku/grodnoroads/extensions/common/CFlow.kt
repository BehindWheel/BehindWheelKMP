package com.egoriku.grodnoroads.extensions.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class CFlow<T : Any>(private val flow: Flow<T>) : Flow<T> by flow

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
open class CStateFlow<T : Any>(private val stateFlow: StateFlow<T>) : CFlow<T>(stateFlow),
    StateFlow<T> by stateFlow

fun <T : Any> StateFlow<T>.asCFlow() = CStateFlow(this)

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
class CMutableStateFlow<T : Any>(
    private val mutableStateFlow: MutableStateFlow<T>
) : CStateFlow<T>(mutableStateFlow), MutableStateFlow<T> by mutableStateFlow {
    constructor(initialValue: T) : this(MutableStateFlow(initialValue))
}