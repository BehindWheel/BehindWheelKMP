package com.egoriku.grodnoroads.screen.map.koin

import com.arkivanov.decompose.ComponentContext
import com.egoriku.grodnoroads.screen.map.MapComponent
import com.egoriku.grodnoroads.screen.map.MapComponentImpl
import com.egoriku.grodnoroads.screen.map.data.MobileCameraRepository
import com.egoriku.grodnoroads.screen.map.data.MobileCameraRepositoryImpl
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory
import org.koin.dsl.module

val mapModule = module {
    factory<MobileCameraRepository> {
        MobileCameraRepositoryImpl(databaseReference = get())
    }

    factory<MapComponent> { (componentContext: ComponentContext) ->
        MapComponentImpl(componentContext = componentContext)
    }

    factory {
        CamerasStoreFactory(
            storeFactory = get(),
            cameraUseCase = get(),
            mobileCameraRepository = get()
        ).create()
    }

    factory {
        LocationStoreFactory(
            storeFactory = get(),
            locationHelper = get(),
            resourceProvider = get()
        ).create()
    }
}

