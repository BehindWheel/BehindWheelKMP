package com.egoriku.grodnoroads.appsettings.domain.util

import androidx.compose.runtime.Composable

interface UrlShare {
    fun share(url: String)
}

@Composable
expect fun rememberUrlShare(): UrlShare