package com.egoriku.grodnoroads.appsettings.domain.util

import androidx.compose.runtime.Composable

interface UrlLauncher {
    fun openUrl(url: String)
}

@Composable
expect fun rememberUrlLauncher(): UrlLauncher
