package com.egoriku.grodnoroads.settings.debugtools.domain.root

import androidx.compose.runtime.Stable

@Stable
interface DebugToolsRootComponent {

    fun onOpenUiKit()
    fun onOpenDataStoreEdit()
    fun onOpenPalette()
    fun onOpenAuth()
    fun onOpenSpecialEvents()
}
