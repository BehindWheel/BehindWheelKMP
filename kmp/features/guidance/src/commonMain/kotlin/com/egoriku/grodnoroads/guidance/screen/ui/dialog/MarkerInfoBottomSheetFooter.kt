package com.egoriku.grodnoroads.guidance.screen.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.close
import com.egoriku.grodnoroads.compose.resources.confirm
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryButton
import com.egoriku.grodnoroads.foundation.uikit.button.SecondaryButton
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun MarkerInfoBottomSheetFooter(
    onConfirm: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SecondaryButton(
            modifier = Modifier.weight(1f),
            text = stringResource(Res.string.close),
            onClick = onClose
        )
        PrimaryButton(
            modifier = Modifier.weight(1f),
            onClick = onConfirm,
            text = stringResource(Res.string.confirm)
        )
    }
}

@PreviewGrodnoRoads
@Composable
private fun PreviewMarkerInfoBottomSheetFooterPreview() = GrodnoRoadsM3ThemePreview {
    MarkerInfoBottomSheetFooter(
        modifier = Modifier.padding(16.dp),
        onConfirm = {},
        onClose = {}
    )
}
