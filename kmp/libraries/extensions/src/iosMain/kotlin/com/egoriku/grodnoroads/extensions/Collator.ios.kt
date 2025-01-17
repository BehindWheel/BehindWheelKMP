package com.egoriku.grodnoroads.extensions

import platform.Foundation.NSString
import platform.Foundation.localizedStandardCompare

object LocalizedStandardComparator : Comparator<String> {
    override fun compare(a: String, b: String): Int = (a as NSString).localizedStandardCompare(b).toInt()
}

actual object Collator {
    actual val collator: Comparator<in String>
        get() = LocalizedStandardComparator
}
