package com.egoriku.grodnoroads.settings.faq.domain.component

import com.egoriku.grodnoroads.extensions.CStateFlow
import com.egoriku.grodnoroads.extensions.LoremIpsum
import com.egoriku.grodnoroads.extensions.stateFlowOf
import com.egoriku.grodnoroads.settings.faq.domain.model.FAQ
import com.egoriku.grodnoroads.settings.faq.domain.store.FaqStore

class FaqComponentPreview : FaqComponent {

    override val state: CStateFlow<FaqStore.State>
        get() = stateFlowOf {
            FaqStore.State(
                isLoading = false,
                faq = listOf(
                    FAQ(
                        question = LoremIpsum.generateLoremIpsum(5),
                        answer = LoremIpsum.generateLoremIpsum(40)
                    ),
                    FAQ(
                        question = LoremIpsum.generateLoremIpsum(8),
                        answer = LoremIpsum.generateLoremIpsum(30)
                    )
                )
            )
        }
}