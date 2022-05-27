package com.egoriku.grodnoroads.screen.map.store.util

import com.egoriku.grodnoroads.extension.distanceTo
import com.egoriku.grodnoroads.screen.map.MapComponent.MapEvent.UserActions

fun List<UserActions>.mergeActions(): List<UserActions> {
    return groupBy { it.mapEventType }
        .mapValues { it.value.merge() }
        .values
        .flatten()
}

private fun List<UserActions>.merge(): List<UserActions> {
    val mergedActions = mutableListOf<UserActions>()

    forEach { action ->
        val index = mergedActions.indexOfFirst { calcAction ->
            val distance = calcAction.position distanceTo action.position

            distance < 100
        }

        if (index != -1) {
            val item = mergedActions[index]

            mergedActions[index] = item.copy(
                message = "${item.message}\n· (${action.time}) ${action.message}",
                position = item.position
            )
        } else {
            mergedActions.add(action.copy(message = "· (${action.time}) ${action.message}"))
        }
    }
    return mergedActions
}