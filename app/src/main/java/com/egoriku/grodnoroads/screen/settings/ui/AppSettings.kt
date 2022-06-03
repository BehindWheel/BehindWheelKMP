package com.egoriku.grodnoroads.screen.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.settings.SettingsWithValue
import com.egoriku.grodnoroads.screen.settings.SettingsComponent.Pref
import com.egoriku.grodnoroads.screen.settings.domain.Theme.Companion.toStringResource
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.SettingsState
import com.egoriku.grodnoroads.screen.settings.ui.common.BasicSettingsSection
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@Composable
fun AppSettings(
    settingsState: SettingsState,
    onClick: (Pref) -> Unit
) {
    BasicSettingsSection(title = stringResource(R.string.settings_section_appearance)) {
        Column {
            val appTheme = settingsState.appTheme

            SettingsWithValue(
                title = {
                    Text(text = stringResource(R.string.settings_app_theme))
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.DarkMode,
                        contentDescription = null
                    )
                },
                onClick = {
                    onClick(appTheme)
                },
                value = stringResource(id = appTheme.current.toStringResource())
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, locale = "ru")
@Composable
fun PreviewAppSettings() {
    GrodnoRoadsTheme {
        AppSettings(SettingsState()) { }
    }
}