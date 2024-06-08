package com.egoriku.grodnoroads.extensions

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

fun openAppSettings() {
    UIApplication.sharedApplication.openURL(NSURL(string = UIApplicationOpenSettingsURLString))
}