package com.egoriku.grodnoroads.appsettings.domain.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.interop.LocalUIViewController
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIPopoverArrowDirectionDown
import platform.UIKit.UIViewController
import platform.UIKit.popoverPresentationController

@Composable
actual fun rememberUrlShare(): UrlShare {
    val uiViewController = LocalUIViewController.current

    return remember { IosUrlShare(uiViewController) }
}

private class IosUrlShare(private val uiViewController: UIViewController) : UrlShare {

    override fun share(url: String) {
        val activityController = UIActivityViewController(
            activityItems = listOf(url),
            applicationActivities = null
        )

        activityController.popoverPresentationController?.apply {
            sourceView = uiViewController.view
            permittedArrowDirections = UIPopoverArrowDirectionDown
        }

        uiViewController.presentViewController(
            viewControllerToPresent = activityController,
            animated = true,
            completion = null
        )
    }
}
