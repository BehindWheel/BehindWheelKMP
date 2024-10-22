package com.egoriku.grodnoroads.shared.components

import kotlin.experimental.ExperimentalNativeApi

actual object DebugConfig {

    @OptIn(ExperimentalNativeApi::class)
    actual val isDebug: Boolean = Platform.isDebugBinary
}
