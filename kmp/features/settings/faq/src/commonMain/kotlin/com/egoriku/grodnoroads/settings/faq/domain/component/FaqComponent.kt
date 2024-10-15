package com.egoriku.grodnoroads.settings.faq.domain.component

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.settings.faq.domain.store.FaqStore
import kotlinx.coroutines.flow.StateFlow

@Stable
interface FaqComponent {

    val state: StateFlow<FaqStore.State>
}
