package com.egoriku.grodnoroads.foundation.common.ui.bottomsheet.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.cancel
import com.egoriku.grodnoroads.compose.resources.ok
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryButton
import com.egoriku.grodnoroads.foundation.uikit.button.TextButton
import org.jetbrains.compose.resources.stringResource

@Composable
fun ConfirmationFooter(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    onAccept: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextButton(modifier = Modifier.weight(1f), onClick = onDismiss) {
            Text(text = stringResource(Res.string.cancel))
        }
        PrimaryButton(modifier = Modifier.weight(1f), onClick = onAccept) {
            Text(text = stringResource(Res.string.ok))
        }
    }
}
