package com.egoriku.grodnoroads.screen.map.store.util

import com.egoriku.grodnoroads.extension.second
import com.egoriku.grodnoroads.extension.third
import com.egoriku.grodnoroads.screen.map.data.model.ReportsResponse
import com.egoriku.grodnoroads.screen.map.domain.MapEventType.RoadIncident
import com.egoriku.grodnoroads.screen.map.domain.MapEventType.TrafficPolice
import com.egoriku.grodnoroads.screen.map.domain.Source.*
import com.egoriku.grodnoroads.util.DateUtil
import com.google.android.gms.maps.model.LatLng
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class MergeReportsTest {

    @Before
    fun init() {
        DateUtil.defaultTimeZone = TimeZone.getTimeZone("GTM")
    }

    @Test
    fun `empty reports`() {
        val reports = emptyList<ReportsResponse>().mapAndMerge()

        assertEquals(0, reports.size)
    }

    @Test
    fun `single report`() {
        val reports = listOf(
            ReportsResponse(
                timestamp = (12.hours + 30.minutes).inWholeMilliseconds,
                message = "Long message",
                source = App.source,
                shortMessage = "Short message",
                latitude = 53.666199,
                longitude = 23.784990,
                type = TrafficPolice.type
            )
        ).mapAndMerge()

        assertEquals(1, reports.size)

        with(reports.first()) {
            assertEquals("(12:30) ${TrafficPolice.emoji} (Short message)", shortMessage)
            assertEquals(TrafficPolice, mapEventType)

            with(messages) {
                assertEquals(1, size)
                assertEquals("(12:30) Long message", first().message)
                assertEquals(App, first().source)
            }
        }
    }

    @Test
    fun `2 different reports, separated distance`() {
        val reports = listOf(
            ReportsResponse(
                timestamp = (12.hours + 30.minutes).inWholeMilliseconds,
                message = "Long message 1",
                source = App.source,
                shortMessage = "Short message 1",
                latitude = 53.666199,
                longitude = 23.784990,
                type = TrafficPolice.type
            ),
            ReportsResponse(
                timestamp = (12.hours + 51.minutes).inWholeMilliseconds,
                message = "Long message 2",
                source = App.source,
                shortMessage = "Short message 2",
                latitude = 53.672628,
                longitude = 23.875794,
                type = RoadIncident.type
            )
        ).mapAndMerge()

        assertEquals(2, reports.size)

        with(reports.first()) {
            assertEquals(TrafficPolice, mapEventType)

            with(messages) {
                assertEquals(1, size)
                assertEquals("(12:30) Long message 1", first().message)
            }

            assertEquals("(12:30) ${TrafficPolice.emoji} (Short message 1)", shortMessage)
            assertEquals(LatLng(53.666199, 23.784990), position)
        }

        with(reports.second()) {
            assertEquals(RoadIncident, mapEventType)

            with(messages) {
                assertEquals(1, size)
                assertEquals("(12:51) Long message 2", first().message)
            }

            assertEquals("(12:51) ${RoadIncident.emoji} (Short message 2)", shortMessage)
            assertEquals(LatLng(53.672628, 23.875794), position)
        }
    }

    @Test
    fun `2 different actions, nearest distance`() {
        val reports = listOf(
            ReportsResponse(
                timestamp = (12.hours + 30.minutes).inWholeMilliseconds,
                message = "Long message 1",
                source = App.source,
                shortMessage = "Short message 1",
                latitude = 53.666199,
                longitude = 23.784990,
                type = TrafficPolice.type
            ),
            ReportsResponse(
                timestamp = (12.hours + 51.minutes).inWholeMilliseconds,
                message = "Long message 2",
                source = Telegram.source,
                shortMessage = "Short message 2",
                latitude = 53.666216,
                longitude = 23.785078,
                type = RoadIncident.type
            )
        ).mapAndMerge()

        assertEquals(2, reports.size)

        with(reports.first()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("(12:30) ${TrafficPolice.emoji} (Short message 1)", shortMessage)
            assertEquals(LatLng(53.666199, 23.784990), position)

            assertEquals(1, messages.size)
            with(messages.first()) {
                assertEquals("(12:30) Long message 1", message)
                assertEquals(App, source)
            }
        }

        with(reports.second()) {
            assertEquals(RoadIncident, mapEventType)
            assertEquals("(12:51) ${RoadIncident.emoji} (Short message 2)", shortMessage)
            assertEquals(LatLng(53.666216, 23.785078), position)

            assertEquals(1, messages.size)
            with(messages.first()) {
                assertEquals("(12:51) Long message 2", message)
                assertEquals(Telegram, source)
            }
        }
    }

    @Test
    fun `2 identical actions, separated location`() {
        val reports = listOf(
            ReportsResponse(
                timestamp = (12.hours + 30.minutes).inWholeMilliseconds,
                message = "Long message 1",
                source = App.source,
                shortMessage = "Short message 1",
                latitude = 53.666199,
                longitude = 23.784990,
                type = TrafficPolice.type
            ),
            ReportsResponse(
                timestamp = (12.hours + 51.minutes).inWholeMilliseconds,
                message = "Long message 2",
                source = Telegram.source,
                shortMessage = "Short message 2",
                latitude = 53.666216,
                longitude = 23.785078,
                type = RoadIncident.type
            )
        ).mapAndMerge()

        assertEquals(2, reports.size)

        with(reports.first()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("(12:30) ${TrafficPolice.emoji} (Short message 1)", shortMessage)
            assertEquals(LatLng(53.666199, 23.784990), position)

            assertEquals(1, messages.size)
            with(messages.first()) {
                assertEquals("(12:30) Long message 1", message)
                assertEquals(App, source)
            }
        }

        with(reports.second()) {
            assertEquals(RoadIncident, mapEventType)
            assertEquals("(12:51) ${RoadIncident.emoji} (Short message 2)", shortMessage)
            assertEquals(LatLng(53.666216, 23.785078), position)

            assertEquals(1, messages.size)
            with(messages.first()) {
                assertEquals("(12:51) Long message 2", message)
                assertEquals(Telegram, source)
            }
        }
    }

    @Test
    fun `2 identical actions, nearest distance`() {
        val reports = listOf(
            ReportsResponse(
                timestamp = (12.hours + 30.minutes).inWholeMilliseconds,
                message = "Long message 1",
                source = App.source,
                shortMessage = "Short message 1",
                latitude = 53.666199,
                longitude = 23.784990,
                type = RoadIncident.type
            ),
            ReportsResponse(
                timestamp = (12.hours + 51.minutes).inWholeMilliseconds,
                message = "Long message 2",
                source = Telegram.source,
                shortMessage = "Short message 2",
                latitude = 53.666216,
                longitude = 23.785078,
                type = RoadIncident.type
            )
        ).mapAndMerge()

        assertEquals(1, reports.size)

        with(reports.first()) {
            assertEquals(RoadIncident, mapEventType)
            assertEquals("(12:30) ${RoadIncident.emoji} (Short message 1)", shortMessage)
            assertEquals(LatLng(53.666199, 23.784990), position)

            assertEquals(2, messages.size)
            with(messages.first()) {
                assertEquals("(12:30) Long message 1", message)
                assertEquals(App, source)
            }

            with(messages.second()) {
                assertEquals("(12:51) Long message 2", message)
                assertEquals(Telegram, source)
            }
        }
    }

    @Test
    fun `2 nearest and 1 away, identical`() {
        val reports = listOf(
            ReportsResponse(
                timestamp = (12.hours + 30.minutes).inWholeMilliseconds,
                message = "Long message 1",
                source = App.source,
                shortMessage = "Short message 1",
                latitude = 53.666199,
                longitude = 23.784990,
                type = TrafficPolice.type
            ),
            ReportsResponse(
                timestamp = (12.hours + 51.minutes).inWholeMilliseconds,
                message = "Long message 2",
                source = Telegram.source,
                shortMessage = "Short message 2",
                latitude = 53.666216,
                longitude = 23.785078,
                type = TrafficPolice.type
            ),
            ReportsResponse(
                timestamp = (12.hours + 52.minutes).inWholeMilliseconds,
                message = "Long message 3",
                source = Viber.source,
                shortMessage = "Short message 3",
                latitude = 53.672628,
                longitude = 23.875794,
                type = TrafficPolice.type
            )
        ).mapAndMerge()

        assertEquals(2, reports.size)

        with(reports.first()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("(12:30) ${TrafficPolice.emoji} (Short message 1)", shortMessage)
            assertEquals(LatLng(53.666199, 23.784990), position)

            assertEquals(2, messages.size)
            with(messages.first()) {
                assertEquals("(12:30) Long message 1", message)
                assertEquals(App, source)
            }

            with(messages.second()) {
                assertEquals("(12:51) Long message 2", message)
                assertEquals(Telegram, source)
            }
        }

        with(reports.second()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("(12:52) ${TrafficPolice.emoji} (Short message 3)", shortMessage)
            assertEquals(LatLng(53.672628, 23.875794), position)

            assertEquals(1, messages.size)
            with(messages.first()) {
                assertEquals("(12:52) Long message 3", message)
                assertEquals(Viber, source)
            }
        }
    }

    @Test
    fun `3 identical action, separated location`() {
        val reports = listOf(
            ReportsResponse(
                timestamp = (12.hours + 30.minutes).inWholeMilliseconds,
                message = "Long message 1",
                source = App.source,
                shortMessage = "Short message 1",
                latitude = 53.666199,
                longitude = 23.784990,
                type = TrafficPolice.type
            ),
            ReportsResponse(
                timestamp = (12.hours + 40.minutes).inWholeMilliseconds,
                message = "Long message 2",
                source = Telegram.source,
                shortMessage = "Short message 2",
                latitude = 53.673657,
                longitude = 23.869954,
                type = TrafficPolice.type
            ),
            ReportsResponse(
                timestamp = (12.hours + 51.minutes).inWholeMilliseconds,
                message = "Long message 3",
                source = Viber.source,
                shortMessage = "Short message 3",
                latitude = 53.719067,
                longitude = 23.850779,
                type = TrafficPolice.type
            )
        ).mapAndMerge()

        assertEquals(3, reports.size)

        with(reports.first()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("(12:30) ${TrafficPolice.emoji} (Short message 1)", shortMessage)
            assertEquals(LatLng(53.666199, 23.784990), position)

            assertEquals(1, messages.size)
            with(messages.first()) {
                assertEquals("(12:30) Long message 1", message)
                assertEquals(App, source)
            }
        }

        with(reports.second()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("(12:40) ${TrafficPolice.emoji} (Short message 2)", shortMessage)
            assertEquals(LatLng(53.673657, 23.869954), position)

            assertEquals(1, messages.size)
            with(messages.first()) {
                assertEquals("(12:40) Long message 2", message)
                assertEquals(Telegram, source)
            }
        }

        with(reports.third()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("(12:51) ${TrafficPolice.emoji} (Short message 3)", shortMessage)
            assertEquals(LatLng(53.719067, 23.850779), position)

            assertEquals(1, messages.size)
            with(messages.first()) {
                assertEquals("(12:51) Long message 3", message)
                assertEquals(Viber, source)
            }
        }
    }

    @Test
    fun `3 identical action, nearest location`() {
        val reports = listOf(
            ReportsResponse(
                timestamp = (12.hours + 30.minutes).inWholeMilliseconds,
                message = "Long message 1",
                source = App.source,
                shortMessage = "Short message 1",
                latitude = 53.673217,
                longitude = 23.871806,
                type = TrafficPolice.type
            ),
            ReportsResponse(
                timestamp = (12.hours + 40.minutes).inWholeMilliseconds,
                message = "Long message 2",
                source = App.source,
                shortMessage = "Short message 2",
                latitude = 53.673187,
                longitude = 23.871366,
                type = TrafficPolice.type
            ),
            ReportsResponse(
                timestamp = (12.hours + 51.minutes).inWholeMilliseconds,
                message = "Long message 3",
                source = App.source,
                shortMessage = "Short message 3",
                latitude = 53.673274,
                longitude = 23.870778,
                type = TrafficPolice.type
            )
        ).mapAndMerge()

        assertEquals(1, reports.size)

        with(reports.first()) {
            assertEquals("(12:30) ${TrafficPolice.emoji} (Short message 1)", shortMessage)
            assertEquals(TrafficPolice, mapEventType)

            assertEquals(3, messages.size)

            with(messages) {
                assertEquals("(12:30) Long message 1", first().message)
                assertEquals("(12:40) Long message 2", second().message)
                assertEquals("(12:51) Long message 3", third().message)
            }
        }
    }
}