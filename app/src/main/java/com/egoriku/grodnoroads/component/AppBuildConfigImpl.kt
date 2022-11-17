package com.egoriku.grodnoroads.component

import com.egoriku.grodnoroads.BuildConfig
import com.egoriku.grodnoroads.shared.appcomponent.AppBuildConfig

class AppBuildConfigImpl : AppBuildConfig {

    override val versionName = BuildConfig.VERSION_NAME
}