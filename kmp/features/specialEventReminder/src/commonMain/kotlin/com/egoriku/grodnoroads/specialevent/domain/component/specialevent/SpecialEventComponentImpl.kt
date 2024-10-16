package com.egoriku.grodnoroads.specialevent.domain.component.specialevent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.extensions.decompose.coroutineScope
import com.egoriku.grodnoroads.extensions.decompose.toStateFlow
import com.egoriku.grodnoroads.specialevent.domain.component.dialog.DialogComponent
import com.egoriku.grodnoroads.specialevent.domain.component.dialog.buildDialogComponent
import com.egoriku.grodnoroads.specialevent.domain.model.EventType
import com.egoriku.grodnoroads.specialevent.domain.store.SpecialEventStore
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun buildSpecialEventComponent(
    componentContext: ComponentContext
): SpecialEventComponent = SpecialEventComponentImpl(componentContext)

internal class SpecialEventComponentImpl(
    componentContext: ComponentContext
) : SpecialEventComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val specialEventStore = instanceKeeper.getStore<SpecialEventStore>(::get)

    private val eventsNavigation = SlotNavigation<EventConfig>()
    private val coroutineScope = coroutineScope()

    init {
        specialEventStore.states
            .mapNotNull { it.eventType }
            .onEach(::showEvent)
            .launchIn(coroutineScope)
    }

    override val specialEvents: StateFlow<ChildSlot<*, DialogComponent>> = childSlot(
        source = eventsNavigation,
        serializer = EventConfig.serializer(),
        handleBackButton = true
    ) { config, childComponentContext ->
        buildDialogComponent(
            componentContext = childComponentContext,
            eventType = config.eventType,
            onDismissed = eventsNavigation::dismiss
        )
    }.toStateFlow()

    private fun showEvent(eventType: EventType) {
        eventsNavigation.activate(EventConfig(eventType))
    }

    @Serializable
    private data class EventConfig(val eventType: EventType)
}
