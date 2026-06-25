package com.egoriku.grodnoroads.eventreporting.screen.ui.foundation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.cancel
import com.egoriku.grodnoroads.compose.resources.send
import com.egoriku.grodnoroads.foundation.core.CenterVerticallyRow
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryButton
import com.egoriku.grodnoroads.foundation.uikit.button.SecondaryButton
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun BottomActions(
    sendEnabled: Boolean,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    onResult: () -> Unit
) {
    CenterVerticallyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
    ) {
        SecondaryButton(
            modifier = Modifier.weight(1f),
            onClick = onCancel
        ) {
            Text(text = stringResource(Res.string.cancel))
        }
        PrimaryButton(
            modifier = Modifier.weight(1f),
            text = stringResource(Res.string.send),
            enabled = sendEnabled,
            onClick = onResult
        )
    }
}
