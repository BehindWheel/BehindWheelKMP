package com.egoriku.grodnoroads.koin

import com.egoriku.grodnoroads.CameraViewModel
import com.egoriku.grodnoroads.MarkerCache
import com.egoriku.grodnoroads.data.repository.ReportActionRepositoryImpl
import com.egoriku.grodnoroads.data.repository.StationaryCameraRepositoryImpl
import com.egoriku.grodnoroads.domain.repository.ReportActionRepository
import com.egoriku.grodnoroads.domain.repository.StationaryCameraRepository
import com.egoriku.grodnoroads.domain.usecase.CameraUseCase
import com.egoriku.grodnoroads.domain.usecase.CameraUseCaseImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {
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

    viewModel {
        CameraViewModel(application = get(), useCase = get())
    }
}
