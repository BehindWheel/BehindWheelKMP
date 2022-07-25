package com.egoriku.grodnoroads.screen.settings.faq.domain.component

import com.egoriku.grodnoroads.screen.settings.faq.domain.store.FaqStore
import kotlinx.coroutines.flow.Flow

interface FaqComponent {

    val state: Flow<FaqStore.State>
}