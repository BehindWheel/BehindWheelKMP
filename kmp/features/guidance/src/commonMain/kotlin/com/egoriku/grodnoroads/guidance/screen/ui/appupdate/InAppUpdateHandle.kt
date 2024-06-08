package com.egoriku.grodnoroads.guidance.screen.ui.appupdate

import androidx.compose.runtime.Composable

@Composable
expect fun InAppUpdateHandle(onDownloaded: (complete: () -> Unit) -> Unit)