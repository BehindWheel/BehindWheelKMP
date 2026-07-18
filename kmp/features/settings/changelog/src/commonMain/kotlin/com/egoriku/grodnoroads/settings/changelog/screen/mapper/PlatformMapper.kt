package com.egoriku.grodnoroads.settings.changelog.screen.mapper

import com.egoriku.grodnoroads.foundation.theme.Platform
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform

internal fun Platform.toChangelogPlatform(): ChangelogPlatform = when (this) {
    Platform.Android -> ChangelogPlatform.Android
    Platform.IOS -> ChangelogPlatform.Ios
}
