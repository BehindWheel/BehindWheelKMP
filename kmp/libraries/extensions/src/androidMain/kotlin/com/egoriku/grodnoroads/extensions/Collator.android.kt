package com.egoriku.grodnoroads.extensions

import java.text.Collator
import java.util.Locale

actual object Collator {
    actual val collator: Comparator<in String>
        get() = Collator.getInstance(Locale.getDefault())
}
