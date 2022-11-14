package com.egoriku.grodnoroads.screen.settings.whatsnew.store

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.screen.settings.whatsnew.store.WhatsNewStore.State

interface WhatsNewStore : Store<Nothing, State, Nothing> {

    sealed class Message {
        data class Loading(val isLoading: Boolean) : Message()
        data class Success(val releaseNotes: List<State.ReleaseNotes>) : Message()
    }

    data class State(
        val isLoading: Boolean = false,
        val releaseNotes: List<ReleaseNotes> = emptyList()
    ) {
        data class ReleaseNotes(
            val versionCode: Int,
            val versionName: String,
            val notes: String
        )
    }
}