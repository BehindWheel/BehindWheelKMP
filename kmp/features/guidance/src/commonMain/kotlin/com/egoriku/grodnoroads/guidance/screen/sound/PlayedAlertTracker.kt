package com.egoriku.grodnoroads.guidance.screen.sound

import com.egoriku.grodnoroads.extensions.DateTime
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class PlayedAlertTracker(
    private val timeProvider: () -> Long = { DateTime.currentTimeMillis() }
) {
    private val history = mutableMapOf<String, Long>()

    fun shouldPlay(id: String, expiration: Long = FIVE_MINUTES): Boolean {
        val currentTime = timeProvider()
        val lastPlayed = history[id] ?: return true
        return currentTime - lastPlayed > expiration
    }

    fun record(id: String) {
        history[id] = timeProvider()
    }

    fun cleanup() {
        val currentTime = timeProvider()
        history.entries.removeAll { currentTime - it.value > THIRTY_MINUTES }
    }

    internal fun size() = history.size

    companion object {
        val FIVE_SECONDS = 5.seconds.inWholeMilliseconds
        val FIVE_MINUTES = 5.minutes.inWholeMilliseconds
        val THIRTY_MINUTES = 30.minutes.inWholeMilliseconds
    }
}
