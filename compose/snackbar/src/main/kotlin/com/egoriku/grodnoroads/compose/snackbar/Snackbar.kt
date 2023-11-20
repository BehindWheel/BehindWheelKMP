package com.egoriku.grodnoroads.compose.snackbar

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview

@Composable
fun Snackbar() {
    Text("test")
}

@Preview
@Composable
private fun SnakbarPreview() = GrodnoRoadsM3ThemePreview {
    Snackbar()
}