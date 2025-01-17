package com.egoriku.grodnoroads.cityselector.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.egoriku.grodnoroads.cityselector.domain.component.CitySelectorComponent.CitySelectorPref
import com.egoriku.grodnoroads.cityselector.domain.store.CitySelectorStore
import com.egoriku.grodnoroads.cityselector.domain.store.CitySelectorStore.Intent
import com.egoriku.grodnoroads.cityselector.domain.store.CitySelectorStore.Label
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun buildCitySelectorComponent(
    componentContext: ComponentContext,
    onFinishIntro: () -> Unit
): CitySelectorComponent = CitySelectorComponentImpl(
    componentContext = componentContext,
    onFinishIntro = onFinishIntro
)

internal class CitySelectorComponentImpl(
    componentContext: ComponentContext,
    private val onFinishIntro: () -> Unit
) : CitySelectorComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val citySelectorStore: CitySelectorStore = instanceKeeper.getStore(::get)

    override val state = citySelectorStore.stateFlow(lifecycle)

    init {
        bind(lifecycle, BinderLifecycleMode.CREATE_DESTROY) {
            citySelectorStore.labels bindTo ::bindLabel
        }
    }

    override fun completeIntro() {
        citySelectorStore.accept(Intent.CompleteIntro)
    }

    override fun modify(preference: CitySelectorPref) {
        citySelectorStore.accept(Intent.Modify(preference))
    }

    private fun bindLabel(label: Label) = when (label) {
        is Label.FinishIntro -> onFinishIntro()
    }
}
