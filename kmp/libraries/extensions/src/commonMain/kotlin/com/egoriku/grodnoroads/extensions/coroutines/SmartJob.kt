package com.egoriku.grodnoroads.extensions.coroutines

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren

/**
 * [Job] delegate which automatically cancels previous instance after setting new value.
 * @param onCancelled called when previous job is about to cancel
 */
private class SmartJobImpl(
    private val onCancelled: (Job) -> Unit = {}
) : ReadWriteProperty<Any?, Job?> {

    private var job: Job? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): Job? = job

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Job?) {
        job?.apply {
            onCancelled(this)
            cancelChildren()
            cancel()
        }
        job = value
    }
}

/**
 * [Job] delegate which automatically cancels previous instance after setting new value.
 * @param onCancelled called when previous job is about to cancel
 */
fun smartJob(
    onCancelled: (Job) -> Unit = {}
): ReadWriteProperty<Any?, Job?> = SmartJobImpl(onCancelled)
