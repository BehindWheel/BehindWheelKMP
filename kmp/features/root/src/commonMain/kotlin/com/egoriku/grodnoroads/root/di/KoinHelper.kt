package com.egoriku.grodnoroads.root.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.egoriku.grodnoroads.crashlytics.di.crashlytics
import com.egoriku.grodnoroads.guidance.di.guidanceModule
import com.egoriku.grodnoroads.mainflow.di.mainFlowModule
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.crashlytics.crashlytics
import dev.gitlive.firebase.database.database
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun appModule() = listOf(
    platformDataStoreModule,
    appScopeModule,
    mainFlowModule,

    // TODO: register all modules
    guidanceModule,

    crashlytics
)

val appScopeModule = module {
    singleOf(::DefaultStoreFactory) { bind<StoreFactory>() }
    single { Firebase.firestore }
    single { Firebase.database.reference() }
    single { Firebase.crashlytics }
}

internal expect val platformDataStoreModule: Module
