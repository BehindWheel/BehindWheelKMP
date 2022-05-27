package com.egoriku.grodnoroads.screen.map.store.util

import com.egoriku.grodnoroads.domain.model.MapEventType.RoadAccident
import com.egoriku.grodnoroads.domain.model.MapEventType.TrafficPolice
import com.egoriku.grodnoroads.domain.model.Source
import com.egoriku.grodnoroads.extension.second
import com.egoriku.grodnoroads.screen.map.MapComponent.MapEvent.UserActions
import com.google.android.gms.maps.model.LatLng
import org.junit.Test
import kotlin.test.assertEquals

class MergeActionsTest {

    @Test
    fun `single action`() {
        val mergedActions = listOf(
            UserActions(
                time = "12:30",
                message = "Long message",
                shortMessage = "Short message",
                source = Source.App,
                position = LatLng(53.666199, 23.784990),
                mapEventType = TrafficPolice
            )
        ).mergeActions()

        assertEquals(1, mergedActions.size)
        assertEquals("· (12:30) Long message", mergedActions.first().message)
    }

    @Test
    fun `2 different actions, separated distance`() {
        val mergedActions = listOf(
            UserActions(
                time = "12:30",
                message = "Long message 1",
                shortMessage = "Short message 1",
                source = Source.App,
                position = LatLng(53.666199, 23.784990),
                mapEventType = TrafficPolice
            ),
            UserActions(
                time = "12:51",
                message = "Long message 2",
                shortMessage = "Short message 2",
                source = Source.App,
                position = LatLng(53.672628, 23.875794),
                mapEventType = RoadAccident
            ),
        ).mergeActions()

        assertEquals(2, mergedActions.size)

        with(mergedActions.first()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("· (12:30) Long message 1", message)
            assertEquals("12:30", time)
            assertEquals("Short message 1", shortMessage)
            assertEquals(LatLng(53.666199, 23.784990), position)
        }

        with(mergedActions.second()) {
            assertEquals(RoadAccident, mapEventType)
            assertEquals("· (12:51) Long message 2", message)
            assertEquals("12:51", time)
            assertEquals("Short message 2", shortMessage)
            assertEquals(LatLng(53.672628, 23.875794), position)
        }
    }

    @Test
    fun `2 different actions, nearest distance`() {
        val mergedActions = listOf(
            UserActions(
                time = "12:30",
                message = "Long message 1",
                shortMessage = "Short message 1",
                source = Source.App,
                position = LatLng(53.666199, 23.784990),
                mapEventType = TrafficPolice
            ),
            UserActions(
                time = "12:51",
                message = "Long message 2",
                shortMessage = "Short message 2",
                source = Source.App,
                position = LatLng(53.666216, 23.785078),
                mapEventType = RoadAccident
            ),
        ).mergeActions()

        assertEquals(2, mergedActions.size)

        with(mergedActions.first()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("· (12:30) Long message 1", message)
            assertEquals("12:30", time)
            assertEquals("Short message 1", shortMessage)
            assertEquals(LatLng(53.666199, 23.784990), position)
        }

        with(mergedActions.second()) {
            assertEquals(RoadAccident, mapEventType)
            assertEquals("· (12:51) Long message 2", message)
            assertEquals("12:51", time)
            assertEquals("Short message 2", shortMessage)
            assertEquals(LatLng(53.666216, 23.785078), position)
        }
    }

    @Test
    fun `2 identical actions, separated location`() {
        val mergedActions = listOf(
            UserActions(
                time = "12:30",
                message = "Long message 1",
                shortMessage = "Short message 1",
                source = Source.App,
                position = LatLng(53.666199, 23.784990),
                mapEventType = TrafficPolice
            ),
            UserActions(
                time = "12:51",
                message = "Long message 2",
                shortMessage = "Short message 2",
                source = Source.App,
                position = LatLng(53.672628, 23.875794),
                mapEventType = TrafficPolice
            ),
        ).mergeActions()

        assertEquals(2, mergedActions.size)

        with(mergedActions.first()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("· (12:30) Long message 1", message)
            assertEquals("12:30", time)
            assertEquals("Short message 1", shortMessage)
            assertEquals(LatLng(53.666199, 23.784990), position)
        }

        with(mergedActions.second()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("· (12:51) Long message 2", message)
            assertEquals("12:51", time)
            assertEquals("Short message 2", shortMessage)
            assertEquals(LatLng(53.672628, 23.875794), position)
        }
    }

    @Test
    fun `2 identical actions, nearest distance`() {
        val mergedActions = listOf(
            UserActions(
                time = "12:30",
                message = "Long message 1",
                shortMessage = "Short message 1",
                source = Source.App,
                position = LatLng(53.666199, 23.784990),
                mapEventType = RoadAccident
            ),
            UserActions(
                time = "12:51",
                message = "Long message 2",
                shortMessage = "Short message 2",
                source = Source.App,
                position = LatLng(53.666216, 23.785078),
                mapEventType = RoadAccident
            ),
        ).mergeActions()

        assertEquals(1, mergedActions.size)

        with(mergedActions.first()) {
            assertEquals(RoadAccident, mapEventType)
            assertEquals("· (12:30) Long message 1\n· (12:51) Long message 2", message)
            assertEquals("12:30", time)
            assertEquals("Short message 1", shortMessage)
            assertEquals(LatLng(53.666199, 23.784990), position)
        }
    }

    @Test
    fun `2 identical nearest and 1 identical`() {
        val mergedActions = listOf(
            UserActions(
                time = "12:30",
                message = "Long message 1",
                shortMessage = "Short message 1",
                source = Source.App,
                position = LatLng(53.666199, 23.784990),
                mapEventType = TrafficPolice
            ),
            UserActions(
                time = "12:40",
                message = "Long message 2",
                shortMessage = "Short message 2",
                source = Source.App,
                position = LatLng(53.666216, 23.785078),
                mapEventType = TrafficPolice
            ),
            UserActions(
                time = "12:51",
                message = "Long message 3",
                shortMessage = "Short message 3",
                source = Source.App,
                position = LatLng(53.672628, 23.875794),
                mapEventType = TrafficPolice
            ),
        ).mergeActions()

        assertEquals(2, mergedActions.size)

        with(mergedActions.first()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("· (12:30) Long message 1\n· (12:40) Long message 2", message)
            assertEquals("12:30", time)
            assertEquals("Short message 1", shortMessage)
            assertEquals(LatLng(53.666199, 23.784990), position)
        }

        with(mergedActions.second()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("· (12:51) Long message 3", message)
            assertEquals("12:51", time)
            assertEquals("Short message 3", shortMessage)
            assertEquals(LatLng(53.672628, 23.875794), position)
        }
    }

    @Test
    fun `3 identical action, separated location`() {
        val mergedActions = listOf(
            UserActions(
                time = "12:30",
                message = "Long message 1",
                shortMessage = "Short message 1",
                source = Source.App,
                position = LatLng(53.666199, 23.784990),
                mapEventType = TrafficPolice
            ),
            UserActions(
                time = "12:40",
                message = "Long message 2",
                shortMessage = "Short message 2",
                source = Source.App,
                position = LatLng(53.673657, 23.869954),
                mapEventType = TrafficPolice
            ),
            UserActions(
                time = "12:51",
                message = "Long message 3",
                shortMessage = "Short message 3",
                source = Source.App,
                position = LatLng(53.719067, 23.850779),
                mapEventType = TrafficPolice
            ),
        ).mergeActions()

        assertEquals(3, mergedActions.size)
    }

    @Test
    fun `3 identical action, nearest location`() {
        val mergedActions = listOf(
            UserActions(
                time = "12:30",
                message = "Long message 1",
                shortMessage = "Short message 1",
                source = Source.App,
                position = LatLng(53.673217, 23.871806),
                mapEventType = TrafficPolice
            ),
            UserActions(
                time = "12:40",
                message = "Long message 2",
                shortMessage = "Short message 2",
                source = Source.App,
                position = LatLng(53.673187, 23.871366),
                mapEventType = TrafficPolice
            ),
            UserActions(
                time = "12:51",
                message = "Long message 3",
                shortMessage = "Short message 3",
                source = Source.App,
                position = LatLng(53.673274, 23.870778),
                mapEventType = TrafficPolice
            )
        ).mergeActions()

        assertEquals(1, mergedActions.size)
    }
}