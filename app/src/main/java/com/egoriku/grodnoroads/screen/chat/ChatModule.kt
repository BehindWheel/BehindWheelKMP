package com.egoriku.grodnoroads.screen.chat

import com.arkivanov.decompose.ComponentContext
import org.koin.dsl.module

val chatModule = module {
    factory<ChatComponent> { (componentContext: ComponentContext) ->
        ChatComponentImpl(componentContext = componentContext)
    }
}