package com.egoriku.grodnoroads.foundation.uikit.bottomsheet.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryButton
import com.egoriku.grodnoroads.foundation.uikit.button.TextButton
import com.egoriku.grodnoroads.resources.R

@Composable
fun ConfirmationFooter(onDismiss: () -> Unit, onAccept: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextButton(modifier = Modifier.weight(1f), onClick = onDismiss) {
            Text(stringResource(id = R.string.cancel))
        }
        PrimaryButton(modifier = Modifier.weight(1f), onClick = onAccept) {
            Text(stringResource(id = R.string.ok))
        }
    }
}