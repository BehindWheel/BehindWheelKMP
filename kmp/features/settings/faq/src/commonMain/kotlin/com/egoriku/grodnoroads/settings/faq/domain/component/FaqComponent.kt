package com.egoriku.grodnoroads.settings.faq.domain.component

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.coroutines.CStateFlow
import com.egoriku.grodnoroads.settings.faq.domain.store.FaqStore

@Stable
interface FaqComponent {

    val state: CStateFlow<FaqStore.State>
}