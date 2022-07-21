package com.egoriku.grodnoroads.screen.settings.faq.store

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.screen.settings.faq.store.FaqStore.State
import com.egoriku.grodnoroads.screen.settings.faq.store.FaqStore.State.FAQ

interface FaqStore : Store<Nothing, State, Nothing> {

    sealed class Message {
        data class Loading(val isLoading: Boolean) : Message()
        data class Success(val faq: List<FAQ>) : Message()
    }

    data class State(
        val isLoading: Boolean = false,
        val faq: List<FAQ> = emptyList()
    ) {
        data class FAQ(
            val question: String,
            val answer: String
        )
    }
}