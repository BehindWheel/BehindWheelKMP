package com.egoriku.grodnoroads.component

import com.egoriku.grodnoroads.BuildConfig
import com.egoriku.grodnoroads.shared.components.AppBuildConfig

class AppBuildConfigImpl : AppBuildConfig {

    override val versionName = BuildConfig.VERSION_NAME
}