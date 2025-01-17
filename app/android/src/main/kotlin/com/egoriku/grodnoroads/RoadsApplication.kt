@file:Suppress("unused")

package com.egoriku.grodnoroads

import android.app.Application
import com.egoriku.grodnoroads.crashlytics.shared.config.CrashlyticsConfig
import com.egoriku.grodnoroads.root.init.FirebaseInit
import com.egoriku.grodnoroads.root.init.KoinInit

class RoadsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseInit.start(this)
        KoinInit.start(this)
        CrashlyticsConfig.isCollectionEnabled(!BuildConfig.DEBUG)
    }
}
