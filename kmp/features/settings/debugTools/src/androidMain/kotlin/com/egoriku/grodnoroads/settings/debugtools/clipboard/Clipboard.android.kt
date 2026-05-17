package com.egoriku.grodnoroads.settings.debugtools.clipboard

import android.content.ClipData
import androidx.compose.ui.platform.ClipEntry

actual fun createClipEntry(text: String): ClipEntry {
    return ClipEntry(ClipData.newPlainText("", text))
}
