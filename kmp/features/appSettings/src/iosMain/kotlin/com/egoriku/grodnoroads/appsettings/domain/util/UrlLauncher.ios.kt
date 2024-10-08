package com.egoriku.grodnoroads.appsettings.domain.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

@Composable
actual fun rememberUrlLauncher(): UrlLauncher {
    return remember { UrlLauncherIos() }
}

private class UrlLauncherIos : UrlLauncher {

    override fun openUrl(url: String) {
        val nsUrl = NSURL.URLWithString(url) ?: return

        if (UIApplication.sharedApplication.canOpenURL(nsUrl)) {
            UIApplication.sharedApplication.openURL(nsUrl)
        }
    }
}