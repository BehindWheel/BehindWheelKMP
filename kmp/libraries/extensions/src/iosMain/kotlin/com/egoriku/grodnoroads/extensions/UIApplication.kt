package com.egoriku.grodnoroads.extensions

import platform.Foundation.NSURL.Companion.URLWithString
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

fun openAppSettings() {
    UIApplication.sharedApplication.openURL(
        url = URLWithString(UIApplicationOpenSettingsURLString)!!,
        options = emptyMap<Any?, Any>(),
        completionHandler = null
    )
}
