package com.egoriku.grodnoroads.settings.debugtools.clipboard

import androidx.compose.ui.platform.ClipEntry

expect fun createClipEntry(text: String): ClipEntry
