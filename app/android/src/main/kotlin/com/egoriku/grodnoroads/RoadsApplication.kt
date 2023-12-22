@file:Suppress("unused")

package com.egoriku.grodnoroads

import android.app.Application
import com.egoriku.grodnoroads.analytics.di.analyticsModule
import com.egoriku.grodnoroads.crashlytics.config.CrashlyticsConfig
import com.egoriku.grodnoroads.koin.appScopeModule
import com.egoriku.grodnoroads.location.di.locationModule
import com.egoriku.grodnoroads.logger.logD
import com.egoriku.grodnoroads.map.data.di.mapDataModule
import com.egoriku.grodnoroads.map.di.mapUiModule
import com.egoriku.grodnoroads.map.domain.di.mapDomainModule
import com.egoriku.grodnoroads.root.di.initKoin
import com.egoriku.grodnoroads.screen.root.koin.rootModule
import com.egoriku.grodnoroads.setting.map.di.mapSettingsModule
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.MapsInitializer.Renderer
import com.google.android.gms.maps.OnMapsSdkInitializedCallback
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RoadsApplication : Application(), OnMapsSdkInitializedCallback {

    override fun onCreate() {
        super.onCreate()
        MapsInitializer.initialize(applicationContext, Renderer.LATEST, this)

        CrashlyticsConfig.isCollectionEnabled(!BuildConfig.DEBUG)
        initKoin(context = this)
    }

    override fun onMapsSdkInitialized(renderer: Renderer) {
        when (renderer) {
            Renderer.LATEST -> logD("The latest version of the renderer is used.")
            Renderer.LEGACY -> logD("The legacy version of the renderer is used.")
        }
    }

    // TODO: remove
    private fun initKoinOld() {
        startKoin {
            androidContext(this@RoadsApplication)
            modules(
                analyticsModule,
                locationModule,

                appScopeModule,

                mapDataModule,
                mapDomainModule,
                mapUiModule,

                mapSettingsModule,

                rootModule,
            )
        }
    }
}