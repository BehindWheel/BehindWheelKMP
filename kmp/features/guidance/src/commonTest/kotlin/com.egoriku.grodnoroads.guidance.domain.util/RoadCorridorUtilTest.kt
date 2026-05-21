package com.egoriku.grodnoroads.guidance.domain.util

import com.egoriku.grodnoroads.location.LatLng
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Tests for [isUserOnCameraRoad].
 *
 * Coordinate math (at lat ≈ 0°, lon ≈ 0°):
 *   1° longitude  ≈ 111 320 m  →  1 m ≈ 8.98e-6 °lon
 *   1° latitude   ≈ 111 000 m  →  1 m ≈ 9.01e-6 °lat
 *
 * Road corridor half-width = 40 m.
 * Tests use 20 m (clearly inside) and 60 m (clearly outside) as safe margins.
 */
class RoadCorridorUtilTest {

    // ---------------------------------------------------------------------------
    // camera angle = 0° (North) — road axis = North-South, cross-track = E/W
    // ---------------------------------------------------------------------------

    @Test
    fun `user at camera position returns true`() {
        val camera = LatLng(0.0, 0.0)
        assertTrue(isUserOnCameraRoad(camera, 0f, camera))
    }

    @Test
    fun `user on road axis to the north returns true`() {
        val camera = LatLng(0.0, 0.0)
        val userNorth = LatLng(0.001, 0.0) // ~111 m north, 0 cross-track
        assertTrue(isUserOnCameraRoad(camera, 0f, userNorth))
    }

    @Test
    fun `user on road axis to the south returns true`() {
        val camera = LatLng(0.0, 0.0)
        val userSouth = LatLng(-0.001, 0.0) // ~111 m south, 0 cross-track
        assertTrue(isUserOnCameraRoad(camera, 0f, userSouth))
    }

    @Test
    fun `user 20m east of north road is inside corridor`() {
        val camera = LatLng(0.0, 0.0)
        val user = LatLng(0.0, 0.0001797) // ~20 m east
        assertTrue(isUserOnCameraRoad(camera, 0f, user))
    }

    @Test
    fun `user 60m east of north road is outside corridor`() {
        val camera = LatLng(0.0, 0.0)
        val user = LatLng(0.0, 0.0005393) // ~60 m east
        assertFalse(isUserOnCameraRoad(camera, 0f, user))
    }

    @Test
    fun `user 20m west of north road is inside corridor`() {
        val camera = LatLng(0.0, 0.0)
        val user = LatLng(0.0, -0.0001797) // ~20 m west
        assertTrue(isUserOnCameraRoad(camera, 0f, user))
    }

    @Test
    fun `user 60m west of north road is outside corridor`() {
        val camera = LatLng(0.0, 0.0)
        val user = LatLng(0.0, -0.0005393) // ~60 m west
        assertFalse(isUserOnCameraRoad(camera, 0f, user))
    }

    // ---------------------------------------------------------------------------
    // Boundary tests at exactly 40 m (ROAD_HALF_WIDTH)
    // 40 m east/west at lat ≈ 0°: 40 / 111_320 ≈ 0.0003594°
    // ---------------------------------------------------------------------------

    @Test
    fun `user exactly 40m east of north road is on corridor boundary (inside)`() {
        val camera = LatLng(0.0, 0.0)
        val user = LatLng(0.0, 0.0003594) // ≈ 40 m east
        assertTrue(isUserOnCameraRoad(camera, 0f, user))
    }

    @Test
    fun `user exactly 40m west of north road is on corridor boundary (inside)`() {
        val camera = LatLng(0.0, 0.0)
        val user = LatLng(0.0, -0.0003594) // ≈ 40 m west
        assertTrue(isUserOnCameraRoad(camera, 0f, user))
    }

    @Test
    fun `user just beyond 40m east of north road is outside corridor`() {
        val camera = LatLng(0.0, 0.0)
        val user = LatLng(0.0, 0.0003685) // ≈ 41 m east
        assertFalse(isUserOnCameraRoad(camera, 0f, user))
    }

    @Test
    fun `user just beyond 40m west of north road is outside corridor`() {
        val camera = LatLng(0.0, 0.0)
        val user = LatLng(0.0, -0.0003685) // ≈ 41 m west
        assertFalse(isUserOnCameraRoad(camera, 0f, user))
    }

    // ---------------------------------------------------------------------------
    // camera angle = 90° (East) — road axis = East-West, cross-track = N/S
    // ---------------------------------------------------------------------------

    @Test
    fun `user on road axis to the east returns true for east-facing camera`() {
        val camera = LatLng(0.0, 0.0)
        val userEast = LatLng(0.0, 0.001) // ~111 m east
        assertTrue(isUserOnCameraRoad(camera, 90f, userEast))
    }

    @Test
    fun `user 20m north of east road is inside corridor`() {
        val camera = LatLng(0.0, 0.0)
        val user = LatLng(0.0001802, 0.0) // ~20 m north
        assertTrue(isUserOnCameraRoad(camera, 90f, user))
    }

    @Test
    fun `user 60m north of east road is outside corridor`() {
        val camera = LatLng(0.0, 0.0)
        val user = LatLng(0.0005405, 0.0) // ~60 m north
        assertFalse(isUserOnCameraRoad(camera, 90f, user))
    }

    @Test
    fun `user 20m south of east road is inside corridor`() {
        val camera = LatLng(0.0, 0.0)
        val user = LatLng(-0.0001802, 0.0) // ~20 m south
        assertTrue(isUserOnCameraRoad(camera, 90f, user))
    }

    @Test
    fun `user 60m south of east road is outside corridor`() {
        val camera = LatLng(0.0, 0.0)
        val user = LatLng(-0.0005405, 0.0) // ~60 m south
        assertFalse(isUserOnCameraRoad(camera, 90f, user))
    }

    // ---------------------------------------------------------------------------
    // camera angle = 180° (South) — same road axis as North, results must match
    // ---------------------------------------------------------------------------

    @Test
    fun `user 20m east of south-facing camera road is inside corridor`() {
        val camera = LatLng(0.0, 0.0)
        val user = LatLng(0.0, 0.0001797) // ~20 m east
        assertTrue(isUserOnCameraRoad(camera, 180f, user))
    }

    @Test
    fun `user 60m east of south-facing camera road is outside corridor`() {
        val camera = LatLng(0.0, 0.0)
        val user = LatLng(0.0, 0.0005393) // ~60 m east
        assertFalse(isUserOnCameraRoad(camera, 180f, user))
    }

    // ---------------------------------------------------------------------------
    // camera at non-zero origin (Grodno area)
    // ---------------------------------------------------------------------------

    @Test
    fun `user on road axis Grodno coordinates`() {
        // Camera on a North-facing road in Grodno
        val camera = LatLng(53.6694, 23.8133)
        // User ~100 m directly north — should be on road
        val userNorth = LatLng(53.6703, 23.8133)
        assertTrue(isUserOnCameraRoad(camera, 0f, userNorth))
    }

    @Test
    fun `user on neighbouring road Grodno coordinates`() {
        // Camera on a North-facing road; user is ~65 m east (parallel street)
        // At lat 53.67°, 1 m ≈ 1/(111320 * cos(53.67°)) ≈ 1.696e-5 °lon
        // 65 m east ≈ 65 * 1.696e-5 ≈ 0.001102°
        val camera = LatLng(53.6694, 23.8133)
        val userParallelStreet = LatLng(53.6700, 23.8144) // ~north + ~65 m east
        assertFalse(isUserOnCameraRoad(camera, 0f, userParallelStreet))
    }
}
