package com.egoriku.grodnoroads.koin

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.egoriku.grodnoroads.data.repository.ReportActionRepositoryImpl
import com.egoriku.grodnoroads.data.repository.StationaryCameraRepositoryImpl
import com.egoriku.grodnoroads.domain.repository.ReportActionRepository
import com.egoriku.grodnoroads.domain.repository.StationaryCameraRepository
import com.egoriku.grodnoroads.domain.usecase.CameraUseCase
import com.egoriku.grodnoroads.domain.usecase.CameraUseCaseImpl
import com.egoriku.grodnoroads.util.MarkerCache
import com.egoriku.grodnoroads.util.location.LocationHelper
import com.egoriku.grodnoroads.util.location.LocationHelperImpl
import org.koin.dsl.module

val koinModule = module {
    single<StoreFactory> { DefaultStoreFactory() }
    single<LocationHelper> { LocationHelperImpl(context = get()) }

    single { MarkerCache(context = get()) }

    factory<StationaryCameraRepository> {
        StationaryCameraRepositoryImpl(api = get())
    }
    factory<ReportActionRepository> {
        ReportActionRepositoryImpl(context = get(), api = get())
    }

    factory<CameraUseCase> {
        CameraUseCaseImpl(
            stationaryCameraRepository = get(),
            reportActionRepository = get()
        )
    }
}
