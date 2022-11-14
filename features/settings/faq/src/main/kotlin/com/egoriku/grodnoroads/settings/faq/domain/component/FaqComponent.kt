package com.egoriku.grodnoroads.settings.faq.domain.component

import com.egoriku.grodnoroads.settings.faq.domain.store.FaqStore
import kotlinx.coroutines.flow.Flow

interface FaqComponent {

    val state: Flow<FaqStore.State>
}