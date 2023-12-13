package com.egoriku.grodnoroads.setting.changelog.di

import com.egoriku.grodnoroads.setting.changelog.data.repository.ChangelogRepositoryImpl
import com.egoriku.grodnoroads.setting.changelog.domain.repository.ChangelogRepository
import com.egoriku.grodnoroads.setting.changelog.domain.store.ChangelogStoreFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val changelogModule = module {
    factoryOf(::ChangelogRepositoryImpl) { bind<ChangelogRepository>() }

    factory {
        ChangelogStoreFactory(
            storeFactory = get(),
            changelogRepository = get(),
            crashlyticsTracker = get()
        ).create()
    }
}