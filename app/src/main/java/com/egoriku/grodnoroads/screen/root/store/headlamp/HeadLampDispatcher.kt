package com.egoriku.grodnoroads.screen.root.store.headlamp

import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampType.*
import java.util.*

private const val MAY = 5
private const val JUNE = 6
private val MAY_RANGE = 25..31
private val JUNE_RANGE = 1..5

private const val AUGUST = 8
private const val SEPTEMBER = 9
private val AUGUST_RANGE = 25..31
private val SEPTEMBER_RANGE = 1..5

enum class HeadLampType {
    None,
    Spring,
    Autumn
}

object HeadLampDispatcher {

    fun calculateType(): HeadLampType {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
        }

        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1

        return when {
            month == MAY && day in MAY_RANGE -> Spring
            month == JUNE && day in JUNE_RANGE -> Spring
            month == AUGUST && day in AUGUST_RANGE -> Autumn
            month == SEPTEMBER && day in SEPTEMBER_RANGE -> Autumn
            else -> None
        }
    }
}