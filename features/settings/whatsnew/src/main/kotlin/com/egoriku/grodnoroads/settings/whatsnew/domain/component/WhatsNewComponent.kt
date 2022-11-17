package com.egoriku.grodnoroads.settings.whatsnew.domain.component

import com.egoriku.grodnoroads.settings.whatsnew.domain.store.WhatsNewStore
import kotlinx.coroutines.flow.Flow

interface WhatsNewComponent {

    val state: Flow<WhatsNewStore.State>
}