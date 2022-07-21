package com.egoriku.grodnoroads.screen.settings.faq

import com.egoriku.grodnoroads.screen.settings.faq.store.FaqStore
import kotlinx.coroutines.flow.Flow

interface FaqComponent {

    val state: Flow<FaqStore.State>
}