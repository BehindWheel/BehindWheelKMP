package com.egoriku.grodnoroads.extensions

expect object Collator {
    val collator: Comparator<in String>
}
