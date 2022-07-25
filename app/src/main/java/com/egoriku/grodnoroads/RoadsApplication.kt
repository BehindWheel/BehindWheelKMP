@file:Suppress("unused")

package com.egoriku.grodnoroads

import android.app.Application
import com.egoriku.grodnoroads.koin.appScopeModule
import com.egoriku.grodnoroads.screen.main.koin.mainModule
import com.egoriku.grodnoroads.screen.map.koin.mapModule
import com.egoriku.grodnoroads.screen.root.koin.rootModule
import com.egoriku.grodnoroads.screen.settings.alerts.di.alertsModule
import com.egoriku.grodnoroads.screen.settings.appearance.di.appearanceModule
import com.egoriku.grodnoroads.screen.settings.faq.di.faqModule
import com.egoriku.grodnoroads.screen.settings.koin.settingsModule
import com.egoriku.grodnoroads.screen.settings.map.di.mapSettingsModule
import com.egoriku.grodnoroads.screen.settings.whatsnew.di.whatsNewModule
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RoadsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@RoadsApplication)
            modules(
                appScopeModule,
                mainModule,
                mapModule,
                rootModule,
                settingsModule,

                alertsModule,
                appearanceModule,
                faqModule,
                mapSettingsModule,
                whatsNewModule
            )
        }
    }
}