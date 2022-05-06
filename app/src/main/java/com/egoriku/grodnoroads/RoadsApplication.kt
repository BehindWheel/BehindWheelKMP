@file:Suppress("unused")

package com.egoriku.grodnoroads

import android.app.Application
import com.egoriku.grodnoroads.extension.logD
import com.egoriku.grodnoroads.koin.koinModule
import com.egoriku.grodnoroads.koin.networkModule
import com.egoriku.grodnoroads.screen.chat.chatModule
import com.egoriku.grodnoroads.screen.main.koin.mainModule
import com.egoriku.grodnoroads.screen.map.koin.mapModule
import com.egoriku.grodnoroads.screen.settings.koin.settingsModule
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RoadsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)

        subscribeToTopic()

        startKoin {
            androidContext(this@RoadsApplication)
            modules(
                chatModule,
                koinModule,
                mainModule,
                mapModule,
                networkModule,
                settingsModule
            )
        }
    }

    private fun subscribeToTopic() {
        Firebase.messaging.subscribeToTopic("new_event")
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    logD("topic \"new_event\" error subscribe")
                }
                logD("topic \"new_event\" subscribed")
            }
    }
}