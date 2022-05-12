package com.egoriku.grodnoroads.screen.chat

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val chatModule = module {
    factoryOf(::ChatComponentImpl) { bind<ChatComponent>() }
}