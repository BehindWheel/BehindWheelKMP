package com.egoriku.grodnoroads.screen.settings.whatsnew

import com.egoriku.grodnoroads.screen.settings.whatsnew.store.WhatsNewStore
import kotlinx.coroutines.flow.Flow

interface WhatsNewComponent {

    val state: Flow<WhatsNewStore.State>

    data class ReleaseNotes(
        val version: String,
        val notes: String
    )
}