package com.egoriku.grodnoroads.settings.root.domain.component

import com.egoriku.grodnoroads.shared.appcomponent.Page

interface SettingsComponent {

    val appVersion: String

    fun open(page: Page)
}