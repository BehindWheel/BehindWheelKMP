package com.egoriku.grodnoroads.extensions.coroutines

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun <T : Any> stateFlowOf(value: () -> T): StateFlow<T> = MutableStateFlow(value = value())
