package com.egoriku.grodnoroads.extensions.decompose

import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Converts decompose [Value] to coroutines [StateFlow]
 */
fun <T : Any> Value<T>.toStateFlow(): StateFlow<T> = ValueStateFlow(this)

private class ValueStateFlow<out T : Any>(private val source: Value<T>) : StateFlow<T> {

    override val value: T
        get() = source.value

    override val replayCache: List<T>
        get() = listOf(source.value)

    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        val flow = MutableStateFlow(source.value)
        val disposable = source.subscribe { flow.value = it }

        try {
            flow.collect(collector)
        } finally {
            disposable.cancel()
        }
    }
}
