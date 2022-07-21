package com.egoriku.grodnoroads.screen.settings.whatsnew

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.screen.settings.whatsnew.store.WhatsNewStore
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class WhatsNewComponentImpl(
    componentContext: ComponentContext
) : WhatsNewComponent, KoinComponent, ComponentContext by componentContext {

    private val whatsNewStore = instanceKeeper.getStore { get<WhatsNewStore>() }

    override val state: Flow<WhatsNewStore.State>
        get() = whatsNewStore.states
}