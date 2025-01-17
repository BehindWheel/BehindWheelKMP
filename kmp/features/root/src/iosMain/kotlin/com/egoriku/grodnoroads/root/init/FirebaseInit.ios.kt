package com.egoriku.grodnoroads.root.init

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

actual object FirebaseInit {

    fun start() {
        Firebase.initialize()
    }
}
