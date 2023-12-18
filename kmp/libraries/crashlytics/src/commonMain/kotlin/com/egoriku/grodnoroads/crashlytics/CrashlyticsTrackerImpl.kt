package com.egoriku.grodnoroads.crashlytics

import dev.gitlive.firebase.crashlytics.FirebaseCrashlytics

internal class CrashlyticsTrackerImpl(
    private val firebaseCrashlytics: FirebaseCrashlytics
) : CrashlyticsTracker {

    override fun recordException(exception: Throwable) {
        firebaseCrashlytics.recordException(exception).also {
            // TODO: use logger
            // logD(exception.message.toString())
        }
    }
}