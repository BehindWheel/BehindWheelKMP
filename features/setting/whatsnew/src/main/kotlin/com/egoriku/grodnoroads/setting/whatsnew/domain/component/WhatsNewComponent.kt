package com.egoriku.grodnoroads.setting.whatsnew.domain.component

import com.egoriku.grodnoroads.setting.whatsnew.domain.store.WhatsNewStore
import kotlinx.coroutines.flow.Flow

interface WhatsNewComponent {

    val state: Flow<WhatsNewStore.State>
}