package com.egoriku.grodnoroads.settings.faq.domain.component

import com.egoriku.grodnoroads.extensions.common.CStateFlow
import com.egoriku.grodnoroads.settings.faq.domain.store.FaqStore

interface FaqComponent {

    val state: CStateFlow<FaqStore.State>
}