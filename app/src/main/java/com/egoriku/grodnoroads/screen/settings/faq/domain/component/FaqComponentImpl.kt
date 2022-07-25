package com.egoriku.grodnoroads.screen.settings.faq.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.screen.settings.faq.domain.store.FaqStore
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class FaqComponentImpl(
    componentContext: ComponentContext
) : FaqComponent, KoinComponent, ComponentContext by componentContext {

    private val faqStore = instanceKeeper.getStore { get<FaqStore>() }

    override val state: Flow<FaqStore.State>
        get() = faqStore.states
}