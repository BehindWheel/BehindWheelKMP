package com.egoriku.grodnoroads.appsettings.domain.util

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberUrlShare(): UrlShare {
    val context = LocalContext.current

    return remember { AndroidUrlShare(context) }
}

private class AndroidUrlShare(private val context: Context) : UrlShare {

    override fun share(url: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(sendIntent, null))
    }
}
