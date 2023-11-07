package com.egoriku.grodnoroads.crashlytics.config

import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics

object CrashlyticsConfig {

    fun isCollectionEnabled(value: Boolean) {
        Firebase.crashlytics.setCrashlyticsCollectionEnabled(value)
    }
}