package com.egoriku.grodnoroads.guidance.domain.store.dialog

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.guidance.domain.model.MapBottomSheet
import com.egoriku.grodnoroads.guidance.domain.store.dialog.DialogStore.Intent
import com.egoriku.grodnoroads.guidance.domain.store.dialog.DialogStore.Message
import com.egoriku.grodnoroads.guidance.domain.store.dialog.DialogStore.State
import com.egoriku.grodnoroads.shared.analytics.AnalyticsTracker
import kotlinx.coroutines.Dispatchers

internal class DialogStoreFactory(
    private val storeFactory: StoreFactory,
    private val analyticsTracker: AnalyticsTracker
) {

    fun create(): DialogStore =
        object :
            DialogStore,
            Store<Intent, State, Message> by storeFactory.create(
                initialState = State(),
                executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                    onIntent<Intent.OpenMarkerInfoDialog> { dialog ->
                        dispatch(
                            Message.OpenDialog(dialog = MapBottomSheet.MarkerInfo(dialog.reports))
                        )
                        analyticsTracker.trackOpenMarkerInfoDialog()
                    }
                    onIntent<Intent.OpenQuickSettings> {
                        dispatch(Message.OpenDialog(dialog = MapBottomSheet.QuickSettings))
                    }
                    onIntent<Intent.CloseDialog> {
                        dispatch(Message.CloseDialog(dialog = MapBottomSheet.None))
                    }
                },
                reducer = { message: Message ->
                    when (message) {
                        is Message.OpenDialog -> copy(mapBottomSheet = message.dialog)
                        is Message.CloseDialog -> copy(mapBottomSheet = message.dialog)
                    }
                }
            ) {}
}
