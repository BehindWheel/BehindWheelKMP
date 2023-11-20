package com.egoriku.grodnoroads.map.popup

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsPref
import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsPref.AppTheme
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
            style = MaterialTheme.typography.titleSmall,
            text = stringResource(R.string.appearance_app_theme)
        )

        val appTheme = quickActionsState.appTheme

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            appTheme.values.forEach { theme ->
                val selected = appTheme.current == theme
                FilterChip(
                    label = {
                        Text(text = stringResource(theme.toStringResource()))
                    },
                    selected = selected,
                    leadingIcon = if (selected) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Localized Description",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                    border = FilterChipDefaults.filterChipBorder(
                        selectedBorderWidth = 1.dp,
                        selectedBorderColor = MaterialTheme.colorScheme.outline,
                        borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        enabled = true,
                        selected = selected
                    ),
                    onClick = {
                        onChanged(appTheme.copy(current = theme))
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