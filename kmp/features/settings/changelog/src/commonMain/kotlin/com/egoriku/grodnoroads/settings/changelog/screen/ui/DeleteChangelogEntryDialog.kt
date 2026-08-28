package com.egoriku.grodnoroads.settings.changelog.screen.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight

@Composable
internal fun DeleteChangelogEntryDialog(
    versionName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Delete version") },
        text = { Text(text = "Are you sure you want to delete $versionName?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun DeleteChangelogEntryDialogPreview() = GrodnoRoadsM3ThemePreview {
    DeleteChangelogEntryDialog(
        versionName = "1.8.0",
        onConfirm = {},
        onDismiss = {}
    )
}
