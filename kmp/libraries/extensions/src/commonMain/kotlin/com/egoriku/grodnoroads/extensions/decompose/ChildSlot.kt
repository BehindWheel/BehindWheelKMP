package com.egoriku.grodnoroads.extensions.decompose

import com.arkivanov.decompose.router.slot.ChildSlot

inline fun <T : Any> ChildSlot<*, T>?.onChild(action: (T) -> Unit) {
    val instance = this?.child?.instance
    if (instance != null) {
        action(instance)
    }
}
