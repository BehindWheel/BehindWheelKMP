package com.egoriku.grodnoroads.screen.map.koin

import com.arkivanov.decompose.ComponentContext
import com.egoriku.grodnoroads.screen.map.MapComponent
import com.egoriku.grodnoroads.screen.map.MapComponentImpl
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory
import org.koin.dsl.module

val mapModule = module {
    factory<MapComponent> { (componentContext: ComponentContext) ->
        MapComponentImpl(componentContext = componentContext)
    }

    factory {
        CamerasStoreFactory(
            storeFactory = get(),
            cameraUseCase = get()
        ).create()
    }

    factory {
        LocationStoreFactory(storeFactory = get(), locationHelper = get()).create()
    }
}

