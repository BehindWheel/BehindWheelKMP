package com.egoriku.grodnoroads.uidemo.ui.palette

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import com.egoriku.grodnoroads.extensions.toast
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight

@Composable
fun Material3Palette(modifier: Modifier = Modifier) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    val colorScheme = MaterialTheme.colorScheme
    val colors = remember {
        listOf(
            Palette(name = "primary", color = colorScheme.primary),
            Palette(name = "onPrimary", color = colorScheme.onPrimary),
            Palette(name = "primaryContainer", color = colorScheme.primaryContainer),
            Palette(name = "onPrimaryContainer", color = colorScheme.onPrimaryContainer),
            Palette(name = "inversePrimary", color = colorScheme.inversePrimary),
            Palette(name = "secondary", color = colorScheme.secondary),
            Palette(name = "onSecondary", color = colorScheme.onSecondary),
            Palette(name = "secondaryContainer", color = colorScheme.secondaryContainer),
            Palette(name = "onSecondaryContainer", color = colorScheme.onSecondaryContainer),
            Palette(name = "tertiary", color = colorScheme.tertiary),
            Palette(name = "onTertiary", color = colorScheme.onTertiary),
            Palette(name = "tertiaryContainer", color = colorScheme.tertiaryContainer),
            Palette(name = "onTertiaryContainer", color = colorScheme.onTertiaryContainer),
            Palette(name = "background", color = colorScheme.background),
            Palette(name = "onBackground", color = colorScheme.onBackground),
            Palette(name = "surface", color = colorScheme.surface),
            Palette(name = "onSurface", color = colorScheme.onSurface),
            Palette(name = "surfaceVariant", color = colorScheme.surfaceVariant),
            Palette(name = "onSurfaceVariant", color = colorScheme.onSurfaceVariant),
            Palette(name = "surfaceTint", color = colorScheme.surfaceTint),
            Palette(name = "inverseSurface", color = colorScheme.inverseSurface),
            Palette(name = "inverseOnSurface", color = colorScheme.inverseOnSurface),
            Palette(name = "error", color = colorScheme.error),
            Palette(name = "onError", color = colorScheme.onError),
            Palette(name = "errorContainer", color = colorScheme.errorContainer),
            Palette(name = "onErrorContainer", color = colorScheme.onErrorContainer),
            Palette(name = "outline", color = colorScheme.outline)
        )
    }

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3)
    ) {
        items(colors) {
            ThemeColor(
                colorName = it.name,
                currentColor = it.color,
                onClick = { colorHex ->
                    clipboardManager.setText(AnnotatedString(colorHex))
                    context.toast("Copied to clipboard")
                }
            )
        }
    }
}

private data class Palette(
    val color: Color,
    val name: String
)

@PreviewGrodnoRoadsDarkLight
@Composable
private fun Material3PalettePreview() = GrodnoRoadsM3ThemePreview {
    Material3Palette()
}
