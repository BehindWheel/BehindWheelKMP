@file:Suppress("unused")

package com.egoriku.grodnoroads

import android.app.Application
import com.egoriku.grodnoroads.crashlytics.shared.config.CrashlyticsConfig
import com.egoriku.grodnoroads.root.di.initKoin

class RoadsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CrashlyticsConfig.isCollectionEnabled(!BuildConfig.DEBUG)
        initKoin(context = this)
    }
}