@file:Suppress("NOTHING_TO_INLINE")

package com.egoriku.grodnoroads.util

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

inline fun String.encodeMessage(): String =
    URLEncoder.encode(this, StandardCharsets.UTF_8.toString())