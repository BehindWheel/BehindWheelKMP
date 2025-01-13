package com.egoriku.grodnoroads.shared.audioplayer

import com.egoriku.grodnoroads.compose.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
actual fun Res.platformUri(path: String): String {
    return getUri(path).removePrefix("file:///android_asset/")
}
