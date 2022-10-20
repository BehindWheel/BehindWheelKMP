package com.egoriku.grodnoroads.koin

import android.content.Context
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.egoriku.grodnoroads.shared.appsettings.extension.dataStore
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appScopeModule = module {
    single { Firebase.database.reference }
    single { Firebase.firestore }
    single { get<Context>().dataStore }

    singleOf(::DefaultStoreFactory) { bind<StoreFactory>() }
}