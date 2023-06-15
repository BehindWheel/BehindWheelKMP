package com.egoriku.grodnoroads.setting.faq.domain.store

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.setting.faq.domain.model.FAQ
import com.egoriku.grodnoroads.setting.faq.domain.store.FaqStore.State

interface FaqStore : Store<Nothing, State, Nothing> {

    sealed class Message {
        data class Loading(val isLoading: Boolean) : Message()
        data class Success(val faq: List<FAQ>) : Message()
    }

    data class State(
        val isLoading: Boolean = false,
        val faq: List<FAQ> = emptyList()
    )
}