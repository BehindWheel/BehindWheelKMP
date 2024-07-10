package com.egoriku.grodnoroads.guidance.screen.ui.google

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.egoriku.grodnoroads.maps.compose.extension.MarkerImage
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.ui.IconGenerator

actual class MarkerGenerator(context: Context) {

    private val iconGenerator = IconGenerator(context)

    actual fun makeIcon(text: String): MarkerImage {
        return BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon(text))
    }
}

@Composable
actual fun rememberMarkerGenerator(): MarkerGenerator {
    val context = LocalContext.current

    return remember { MarkerGenerator(context) }
}