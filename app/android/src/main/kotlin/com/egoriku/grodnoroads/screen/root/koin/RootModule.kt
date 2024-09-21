package com.egoriku.grodnoroads.screen.root.koin

import com.egoriku.grodnoroads.screen.root.migration.MigrationRepository
import com.egoriku.grodnoroads.screen.root.store.RootStoreFactory
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val rootModule = module {
    factory {
        RootStoreFactory(
            storeFactory = get(),
            dataStore = get(),
            migrationRepository = get()
        ).create()
    }

    factoryOf(::MigrationRepository)
}