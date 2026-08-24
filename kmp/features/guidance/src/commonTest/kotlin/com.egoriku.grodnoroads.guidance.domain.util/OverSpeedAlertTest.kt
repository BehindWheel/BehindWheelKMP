package com.egoriku.grodnoroads.guidance.domain.util

import com.egoriku.grodnoroads.guidance.domain.model.Alert
import com.egoriku.grodnoroads.guidance.domain.model.CameraType
import com.egoriku.grodnoroads.guidance.domain.model.LastLocation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlinx.coroutines.test.runTest

class OverSpeedAlertTest {

    private val transformation = overSpeedTransformation()

    @Test
    fun `no alerts returns None`() = runTest {
        val result = transformation(emptyList(), lastLocation(speed = 100))
        assertIs<OverSpeedAlert.None>(result)
    }

    @Test
    fun `speed below limit returns None`() = runTest {
        val alerts = listOf(cameraAlert(speedLimit = 60, distance = 100))
        val result = transformation(alerts, lastLocation(speed = 60))
        assertIs<OverSpeedAlert.None>(result)
    }

    @Test
    fun `speed just above limit but below threshold returns None`() = runTest {
        // ALLOWED_OVER_SPEED = 2, so speed 62 < 60 + 2 = 62, not overspeed
        val alerts = listOf(cameraAlert(speedLimit = 60, distance = 100))
        val result = transformation(alerts, lastLocation(speed = 61))
        assertIs<OverSpeedAlert.None>(result)
    }

    @Test
    fun `speed above threshold returns Regular when far from camera`() = runTest {
        val alerts = listOf(cameraAlert(speedLimit = 60, distance = 200))
        val result = transformation(alerts, lastLocation(speed = 70))
        assertIs<OverSpeedAlert.Regular>(result)
    }

    @Test
    fun `speed above threshold returns Double when close to camera`() = runTest {
        val alerts = listOf(cameraAlert(speedLimit = 60, distance = 100))
        val result = transformation(alerts, lastLocation(speed = 70))
        assertIs<OverSpeedAlert.Double>(result)
    }

    @Test
    fun `chooses nearest camera when multiple overspeed cameras`() = runTest {
        val alerts = listOf(
            cameraAlert(id = "far", speedLimit = 60, distance = 500),
            cameraAlert(id = "near", speedLimit = 60, distance = 50)
        )
        val result = transformation(alerts, lastLocation(speed = 70))
        val double = result as OverSpeedAlert.Double
        assertEquals("near", double.cameraId)
    }

    @Test
    fun `Double threshold is 150 meters`() = runTest {
        // Exactly at threshold should be Double
        val atThreshold = transformation(
            listOf(cameraAlert(speedLimit = 60, distance = 150)),
            lastLocation(speed = 70)
        )
        assertIs<OverSpeedAlert.Double>(atThreshold)

        // Just above threshold should be Regular
        val justAbove = transformation(
            listOf(cameraAlert(speedLimit = 60, distance = 151)),
            lastLocation(speed = 70)
        )
        assertIs<OverSpeedAlert.Regular>(justAbove)
    }

    private fun lastLocation(speed: Int) = LastLocation(
        latLng = LastLocation.UNKNOWN_LOCATION,
        bearing = 0f,
        speed = speed
    )

    private fun cameraAlert(
        id: String = "camera",
        speedLimit: Int = 60,
        distance: Int = 100
    ) = Alert.CameraAlert(
        cameraType = CameraType.StationaryCamera,
        distance = distance,
        speedLimit = speedLimit,
        id = id
    )
}
