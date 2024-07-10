package com.egoriku.grodnoroads.appsettings.domain.util

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

@Composable
actual fun rememberUrlLauncher(): UrlLauncher {
    val context = LocalContext.current

    return remember { AndroidUrlLauncher(context) }
}

private class AndroidUrlLauncher(private val context: Context) : UrlLauncher {

    override fun openUrl(url: String) {
        CustomTabsIntent.Builder()
            .setShareState(CustomTabsIntent.SHARE_STATE_OFF)
            .build()
            .launchUrl(context, url.toUri())
    }
}