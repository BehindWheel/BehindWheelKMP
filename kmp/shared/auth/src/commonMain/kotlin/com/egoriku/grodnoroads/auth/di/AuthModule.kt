package com.egoriku.grodnoroads.auth.di

import com.egoriku.grodnoroads.auth.Auth
import com.egoriku.grodnoroads.auth.AuthImpl
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authModule = module {
    single { Firebase.auth }
    singleOf(::AuthImpl) { bind<Auth>() }
}
