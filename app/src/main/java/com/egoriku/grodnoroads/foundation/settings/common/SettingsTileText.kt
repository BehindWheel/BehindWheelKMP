package com.egoriku.grodnoroads.foundation.settings.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RowScope.SettingsTileText(title: @Composable () -> Unit) {
    Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.Center,
    ) {
        ProvideTextStyle(value = MaterialTheme.typography.subtitle1) {
            title()
        }
    }
}
