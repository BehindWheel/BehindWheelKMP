package com.egoriku.grodnoroads.crashlytics.config

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

object CrashlyticsConfig {

    fun isCollectionEnabled(value: Boolean) {
        Firebase.crashlytics.setCrashlyticsCollectionEnabled(value)
    }
}