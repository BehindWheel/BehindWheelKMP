package com.egoriku.grodnoroads.foundation.common.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.shared.resources.MR
import dev.icerock.moko.resources.compose.painterResource

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
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            .copy(scrolledContainerColor = MaterialTheme.colorScheme.surface),
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(MR.images.ic_close),
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