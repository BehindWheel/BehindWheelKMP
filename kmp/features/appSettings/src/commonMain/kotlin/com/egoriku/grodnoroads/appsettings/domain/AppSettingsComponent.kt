package com.egoriku.grodnoroads.appsettings.domain

import com.egoriku.grodnoroads.shared.models.Page
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("AppSettingsComponent", exact = true)
interface AppSettingsComponent {

    val appVersion: String

    fun open(page: Page)
}