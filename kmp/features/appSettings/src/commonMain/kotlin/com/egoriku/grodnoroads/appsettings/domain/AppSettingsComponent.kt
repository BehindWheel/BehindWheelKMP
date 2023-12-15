package com.egoriku.grodnoroads.appsettings.domain

import com.egoriku.grodnoroads.shared.models.Page

interface AppSettingsComponent {

    val appVersion: String

    fun open(page: Page)
}