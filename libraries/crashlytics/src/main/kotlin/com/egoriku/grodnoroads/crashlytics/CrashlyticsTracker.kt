package com.egoriku.grodnoroads.crashlytics

interface CrashlyticsTracker {

    fun recordException(exception: Throwable)
}