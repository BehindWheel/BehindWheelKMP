package com.egoriku.grodnoroads.foundation.common.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopBar(
    title: String,
    onBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        windowInsets = WindowInsets.statusBars,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            }
        },
        title = {
            Text(text = title)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@GrodnoRoadsPreview
@Composable
private fun SettingsTopBarPreview() = GrodnoRoadsM3ThemePreview {
    SettingsTopBar(
        title = "Test",
        onBack = {},
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    )
}