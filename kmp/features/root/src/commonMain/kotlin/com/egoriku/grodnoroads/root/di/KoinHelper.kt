package com.egoriku.grodnoroads.root.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.egoriku.grodnoroads.crashlytics.shared.di.crashlyticsModule
import com.egoriku.grodnoroads.eventreporting.di.eventReportingModule
import com.egoriku.grodnoroads.guidance.di.guidanceModule
import com.egoriku.grodnoroads.onboarding.di.onboardingModule
import com.egoriku.grodnoroads.quicksettings.di.quickSettingsModule
import com.egoriku.grodnoroads.settings.alerts.di.alertsModule
import com.egoriku.grodnoroads.settings.appearance.di.appearanceModule
import com.egoriku.grodnoroads.settings.changelog.domain.di.changelogModule
import com.egoriku.grodnoroads.settings.faq.di.faqModule
import com.egoriku.grodnoroads.settings.map.di.mapSettingsModule
import com.egoriku.grodnoroads.shared.analytics.di.analyticsModule
import com.egoriku.grodnoroads.specialevent.di.specialEventModule
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.crashlytics.crashlytics
import dev.gitlive.firebase.database.database
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun appModule() = listOf(
    onboardingModule,

    guidanceModule,
    eventReportingModule,
    quickSettingsModule,
    specialEventModule,

    alertsModule,
    appearanceModule,
    changelogModule,
    faqModule,
    mapSettingsModule,

    appScopeModule,
    analyticsModule,
    crashlyticsModule,
    platformDataStoreModule,
)

val appScopeModule = module {
    singleOf(::DefaultStoreFactory) { bind<StoreFactory>() }
    single { Firebase.firestore }
    single { Firebase.database.reference() }
    single { Firebase.crashlytics }
}

internal expect val platformDataStoreModule: Module
