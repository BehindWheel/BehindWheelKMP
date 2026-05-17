package com.egoriku.grodnoroads.settings.debugtools.clipboard

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ClipEntry

@OptIn(ExperimentalComposeUiApi::class)
actual fun createClipEntry(text: String): ClipEntry {
    return ClipEntry.withPlainText(text)
}
