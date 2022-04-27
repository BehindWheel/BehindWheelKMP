package com.egoriku.grodnoroads.fcm

import androidx.datastore.preferences.core.edit
import com.egoriku.grodnoroads.preferences.NEW_TOPIC
import com.egoriku.grodnoroads.preferences.dataStore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NewEventFirebaseMessagingService : FirebaseMessagingService(), CoroutineScope {

    override val coroutineContext = SupervisorJob()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.from == "/topics/new_event") {
            launch {
                dataStore.edit {
                    it[NEW_TOPIC] = true
                }
            }
        }
    }

    override fun onDestroy() {
        coroutineContext.cancel()
        super.onDestroy()
    }
}