package com.egoriku.grodnoroads.settings.changelog.data.mapper

import com.egoriku.grodnoroads.settings.changelog.data.dto.ChangelogDTO
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform
import com.egoriku.grodnoroads.settings.changelog.domain.model.NewChangelogEntry
import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.fromMilliseconds
import dev.gitlive.firebase.firestore.toMilliseconds
import kotlin.test.Test
import kotlin.test.assertEquals

class ChangelogMapperTest {

    @Test
    fun `toDTO maps release date millis to Timestamp`() {
        val releaseDateMillis = 1_735_689_600_000L // 2025-01-01T00:00:00Z

        val entry = NewChangelogEntry(
            versionName = "1.7.0",
            notes = "Some notes",
            releaseDateMillis = releaseDateMillis
        )

        val dto = entry.toDTO(ChangelogPlatform.Android)

        assertEquals("1.7.0", dto.name)
        assertEquals("Some notes", dto.notes)
        assertEquals("android", dto.platform)
        assertEquals(releaseDateMillis, dto.releaseDate.toMilliseconds().toLong())
    }

    @Test
    fun `toEntry maps DTO fields and Timestamp back to millis`() {
        val releaseDateMillis = 1_735_689_600_000L

        val dto = ChangelogDTO(
            name = "1.7.0",
            notes = "Some notes",
            releaseDate = Timestamp.fromMilliseconds(releaseDateMillis.toDouble()),
            platform = ChangelogPlatform.Ios.query
        )

        val entry = dto.toEntry(id = "doc-1", platform = ChangelogPlatform.Ios)

        assertEquals("doc-1", entry.id)
        assertEquals(ChangelogPlatform.Ios, entry.platform)
        assertEquals("1.7.0", entry.versionName)
        assertEquals("Some notes", entry.notes)
        assertEquals(releaseDateMillis, entry.releaseDateMillis)
    }

    @Test
    fun `toDTO and toEntry round trip preserves release date millis`() {
        val releaseDateMillis = 1_700_000_000_000L

        val newEntry = NewChangelogEntry(
            versionName = "1.6.0",
            notes = "Notes",
            releaseDateMillis = releaseDateMillis
        )

        val roundTripped = newEntry
            .toDTO(ChangelogPlatform.Android)
            .toEntry(id = "doc-2", platform = ChangelogPlatform.Android)

        assertEquals(releaseDateMillis, roundTripped.releaseDateMillis)
    }
}
