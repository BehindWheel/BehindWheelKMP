package com.egoriku.grodnoroads.shared.components

import android.app.Application
import android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual object AppBuildConfig : KoinComponent {

    private val application: Application by inject()

    private val packageManager = application.packageManager
    private val applicationInfo = packageManager.getApplicationInfo(application.packageName, 0)
    private val packageInfo = packageManager.getPackageInfo(application.packageName, 0)

    actual val versionName: String = packageInfo.versionName.orEmpty()

    actual val isDebug: Boolean = (applicationInfo.flags and FLAG_DEBUGGABLE) != 0
}
