package com.egoriku.grodnoroads.guidance.screen.sound

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PlayedAlertTrackerTest {

    private var currentTime = 1000L
    private val tracker = PlayedAlertTracker { currentTime }

    @Test
    fun `shouldPlay new alert returns true`() {
        assertTrue(tracker.shouldPlay("alert_1"))
    }

    @Test
    fun `shouldPlay recently played alert returns false`() {
        tracker.record("alert_1")

        assertFalse(tracker.shouldPlay("alert_1"))
    }

    @Test
    fun `shouldPlay after default expiration returns true`() {
        tracker.record("alert_1")

        currentTime += PlayedAlertTracker.FIVE_MINUTES + 1

        assertTrue(tracker.shouldPlay("alert_1"))
    }

    @Test
    fun `shouldPlay with custom expiration`() {
        val customExpiration = 10_000L
        tracker.record("alert_1")

        currentTime += customExpiration - 1
        assertFalse(tracker.shouldPlay("alert_1", customExpiration))

        currentTime += 2
        assertTrue(tracker.shouldPlay("alert_1", customExpiration))
    }

    @Test
    fun `shouldPlay different alerts are independent`() {
        tracker.record("alert_1")

        assertTrue(tracker.shouldPlay("alert_2"))
    }

    @Test
    fun `cleanup removes old entries`() {
        tracker.record("alert_1")
        tracker.record("alert_2")

        currentTime += PlayedAlertTracker.THIRTY_MINUTES + 1
        tracker.cleanup()

        assertEquals(0, tracker.size())
    }

    @Test
    fun `cleanup keeps recent entries`() {
        tracker.record("alert_1")

        currentTime += PlayedAlertTracker.THIRTY_MINUTES - 1
        tracker.cleanup()

        assertEquals(1, tracker.size())
    }

    @Test
    fun `record updates timestamp`() {
        tracker.record("alert_1")
        assertFalse(tracker.shouldPlay("alert_1"))

        currentTime += PlayedAlertTracker.FIVE_MINUTES + 1
        assertTrue(tracker.shouldPlay("alert_1"))
    }

    @Test
    fun `shouldPlay with zero expiration always returns true for existing alert`() {
        tracker.record("alert_1")

        currentTime += 1
        assertTrue(tracker.shouldPlay("alert_1", expiration = 0))
    }
}
