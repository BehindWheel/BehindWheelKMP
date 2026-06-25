package com.egoriku.grodnoroads.settings.debugtools.ui.datastore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryButton

@Composable
internal fun DataStoreEdit(
    resetOnboarding: () -> Unit,
    modifier: Modifier = Modifier,
    resetReportingLimit: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PrimaryButton(
            text = "Reset onboarding",
            onClick = resetOnboarding
        )
        PrimaryButton(
            text = "Reset reporting limit",
            onClick = resetReportingLimit
        )
    }
}
