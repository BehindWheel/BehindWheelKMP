package com.egoriku.grodnoroads.eventreporting.di

import com.egoriku.grodnoroads.eventreporting.data.repository.ReportingRepositoryImpl
import com.egoriku.grodnoroads.eventreporting.domain.repository.ReportingRepository
import com.egoriku.grodnoroads.eventreporting.domain.store.ReportingStoreFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val eventReportingModule = module {
    factoryOf(::ReportingRepositoryImpl) { bind<ReportingRepository>() }

    factory {
        ReportingStoreFactory(
            storeFactory = get(),
            reportingRepository = get(),
            analyticsTracker = get()
        ).create()
    }
}