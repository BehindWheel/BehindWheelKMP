package com.egoriku.grodnoroads.mainflow.di

import com.egoriku.grodnoroads.settings.changelog.domain.di.changelogModule
import com.egoriku.grodnoroads.settings.faq.di.faqModule
import org.koin.dsl.module

val mainFlowModule = module {
    includes(changelogModule, faqModule)
}