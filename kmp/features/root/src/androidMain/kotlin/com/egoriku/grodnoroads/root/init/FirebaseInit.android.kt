package com.egoriku.grodnoroads.root.init

import android.content.Context
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

actual object FirebaseInit {

    fun start(context: Context) {
        Firebase.initialize(context)
    }
}
