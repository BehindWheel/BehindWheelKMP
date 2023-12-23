package com.egoriku.grodnoroads.koin

import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore
import org.koin.dsl.module

val appScopeModule = module {
    single { Firebase.database.reference }
    single { Firebase.firestore }
}