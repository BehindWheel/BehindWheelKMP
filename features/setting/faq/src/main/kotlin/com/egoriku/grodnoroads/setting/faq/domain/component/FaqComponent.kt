package com.egoriku.grodnoroads.setting.faq.domain.component

import com.egoriku.grodnoroads.setting.faq.domain.store.FaqStore
import kotlinx.coroutines.flow.Flow

interface FaqComponent {

    val state: Flow<FaqStore.State>
}