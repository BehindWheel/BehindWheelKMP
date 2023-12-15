package com.egoriku.grodnoroads.koin

import android.content.Context
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.egoriku.grodnoroads.component.AppBuildConfigImpl
import com.egoriku.grodnoroads.datastore.dataStore
import com.egoriku.grodnoroads.shared.appcomponent.AppBuildConfig
import com.egoriku.grodnoroads.shared.components.AppBuildConfig
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appScopeModule = module {
    single { Firebase.database.reference }
    single { Firebase.firestore }
    single { get<Context>().dataStore() }

    singleOf(::AppBuildConfigImpl) { bind<AppBuildConfig>() }

    singleOf(::DefaultStoreFactory) { bind<StoreFactory>() }
}