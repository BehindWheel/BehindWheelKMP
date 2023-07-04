@file:Suppress("unused")

package com.egoriku.grodnoroads

import android.app.Application
import com.egoriku.grodnoroads.analytics.di.analyticsModule
import com.egoriku.grodnoroads.crashlytics.config.CrashlyticsConfig
import com.egoriku.grodnoroads.crashlytics.di.crashlyticsModule
import com.egoriku.grodnoroads.koin.appScopeModule
import com.egoriku.grodnoroads.location.di.locationModule
import com.egoriku.grodnoroads.map.data.di.mapDataModule
import com.egoriku.grodnoroads.map.di.mapUiModule
import com.egoriku.grodnoroads.map.domain.di.mapDomainModule
import com.egoriku.grodnoroads.screen.root.koin.rootModule
import com.egoriku.grodnoroads.setting.alerts.di.alertsModule
import com.egoriku.grodnoroads.setting.appearance.di.appearanceModule
import com.egoriku.grodnoroads.setting.faq.di.faqModule
import com.egoriku.grodnoroads.setting.map.di.mapSettingsModule
import com.egoriku.grodnoroads.setting.whatsnew.di.whatsNewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RoadsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        CrashlyticsConfig.isCollectionEnabled(!BuildConfig.DEBUG)

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@RoadsApplication)
            modules(
                analyticsModule,
                crashlyticsModule,
                locationModule,

                appScopeModule,

                mapDataModule,
                mapDomainModule,
                mapUiModule,

                alertsModule,
                appearanceModule,
                faqModule,
                mapSettingsModule,
                whatsNewModule,

                rootModule,
            )
        }
    }
}