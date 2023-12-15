package com.egoriku.grodnoroads.crashlytics

import com.egoriku.grodnoroads.extensions.logD
import com.google.firebase.crashlytics.FirebaseCrashlytics

internal class CrashlyticsTrackerImpl(
    private val firebaseCrashlytics: FirebaseCrashlytics
) : CrashlyticsTracker {

    override fun recordException(exception: Throwable) {
        firebaseCrashlytics.recordException(exception).also {
            logD(exception.message.toString())
        }
    }
}