package com.egoriku.grodnoroads.map.popup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.button.SegmentedButton
import com.egoriku.grodnoroads.foundation.uikit.button.SegmentedButtonDefaults
import com.egoriku.grodnoroads.foundation.uikit.button.SegmentedButtonRow
import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsPref
import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsPref.*
import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsState
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme.Companion.toStringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionsContent(
    quickActionsState: QuickActionsState,
    onChanged: (QuickActionsPref) -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = stringResource(R.string.appearance_app_theme)
        )

        val appTheme = quickActionsState.appTheme
        SegmentedButtonRow {
            appTheme.values.forEachIndexed { index, theme ->
                val checked = appTheme.current == theme
                SegmentedButton(
                    colors = SegmentedButtonDefaults.colors(
                        checkedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    ),
                    checked = checked,
                    shape = SegmentedButtonDefaults.shape(
                        position = index,
                        count = appTheme.values.size
                    ),
                    onClick = {
                        onChanged(appTheme.copy(current = theme))
                    },
                    content = {
                        Text(text = stringResource(theme.toStringResource()))
                    }
                )
            }
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun ActionsContentPreview() = GrodnoRoadsM3ThemePreview {
    var quickActionsState by rememberMutableState { QuickActionsState() }

    ActionsContent(
        quickActionsState = quickActionsState,
        onChanged = {
            quickActionsState = when (it) {
                is AppTheme -> quickActionsState.copy(appTheme = it)
            }
        }
    )
}