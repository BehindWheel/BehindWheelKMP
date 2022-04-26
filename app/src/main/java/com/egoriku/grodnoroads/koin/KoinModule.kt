package com.egoriku.grodnoroads.koin

import com.egoriku.grodnoroads.CameraViewModel
import com.egoriku.grodnoroads.MarkerCache
import com.egoriku.grodnoroads.data.ReportActionRepositoryImpl
import com.egoriku.grodnoroads.data.StationaryCameraRepositoryImpl
import com.egoriku.grodnoroads.domain.repository.ReportActionRepository
import com.egoriku.grodnoroads.domain.repository.StationaryCameraRepository
import com.egoriku.grodnoroads.domain.usecase.CameraUseCase
import com.egoriku.grodnoroads.domain.usecase.CameraUseCaseImpl
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {
    single { MarkerCache(context = get()) }
    single { Firebase.database }

    factory<StationaryCameraRepository> {
        StationaryCameraRepositoryImpl(db = get())
    }
    factory<ReportActionRepository> {
        ReportActionRepositoryImpl(db = get())
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
