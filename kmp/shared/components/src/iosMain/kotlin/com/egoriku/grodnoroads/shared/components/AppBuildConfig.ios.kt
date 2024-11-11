package com.egoriku.grodnoroads.shared.components

import kotlin.experimental.ExperimentalNativeApi
import platform.Foundation.NSBundle

actual object AppBuildConfig {

    actual val versionName: String = NSBundle.mainBundle.infoDictionary
        ?.get("CFBundleShortVersionString") as? String
        ?: ""

    @OptIn(ExperimentalNativeApi::class)
    actual val isDebug: Boolean = Platform.isDebugBinary
}
