package com.egoriku.grodnoroads.quicksettings.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.egoriku.grodnoroads.foundation.common.ui.bottomsheet.BasicModalBottomSheet
import com.egoriku.grodnoroads.foundation.common.ui.bottomsheet.rememberSheetCloseBehaviour
import com.egoriku.grodnoroads.quicksettings.domain.component.QuickSettingsComponent
import com.egoriku.grodnoroads.quicksettings.domain.model.QuickSettingsState
import com.egoriku.grodnoroads.quicksettings.screen.ui.QuickSettingsContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickSettingsBottomSheet(
    component: QuickSettingsComponent,
    onClose: () -> Unit
) {
    val quickSettingsState by component.quickSettingsState.collectAsState(QuickSettingsState())

    BasicModalBottomSheet(
        sheetState = rememberSheetCloseBehaviour(onCancel = onClose).sheetState,
        onCancel = onClose,
        content = {
            QuickSettingsContent(
                quickSettingsState = quickSettingsState,
                onChanged = component::updatePreferences
            )
        }
    )
}
