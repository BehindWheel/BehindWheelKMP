package com.egoriku.grodnoroads.appsettings.domain.component

import com.egoriku.grodnoroads.shared.models.Page

class AppSettingsComponentPreview : AppSettingsComponent {

    override val appVersion = "1.0.0"

    override fun open(page: Page) = Unit
}
