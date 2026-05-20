package com.egoriku.grodnoroads.foundation.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.egoriku.grodnoroads.foundation.theme.Platform

/**
 * A [PreviewParameterProvider] that supplies both [Platform.Android] and [Platform.IOS] values,
 * so a single preview function annotated with `@Preview` and
 * `@PreviewParameter(PlatformPreviewProvider::class)` will render once per platform.
 *
 * Usage:
 * ```kotlin
 * @Preview
 * @Composable
 * fun MyPreview(
 *     @PreviewParameter(PlatformPreviewProvider::class) platform: Platform
 * ) {
 *     GrodnoRoadsM3ThemePreview(platform = platform) {
 *         MyComponent()
 *     }
 * }
 * ```
 */
class PlatformPreviewProvider : PreviewParameterProvider<Platform> {
    override val values = sequenceOf(Platform.Android, Platform.IOS)
}
