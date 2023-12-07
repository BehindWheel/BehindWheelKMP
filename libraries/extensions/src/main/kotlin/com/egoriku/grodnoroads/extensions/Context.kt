@file:Suppress("NOTHING_TO_INLINE")

package com.egoriku.grodnoroads.extensions

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources

val Context.audioManager: AudioManager
    get() = getSystemService(Context.AUDIO_SERVICE) as AudioManager

fun Context.openAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", packageName, null)
    }
    startActivity(intent)
}

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

inline fun Context.drawableCompat(id: Int) = AppCompatResources.getDrawable(this, id)