@file:Suppress("NOTHING_TO_INLINE")

package com.egoriku.grodnoroads.extensions

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.media.AudioManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.content.res.AppCompatResources

inline val Context.audioManager: AudioManager
    get() = getSystemService(Context.AUDIO_SERVICE) as AudioManager

inline val Context.locationManager: LocationManager
    get() = getSystemService(Context.LOCATION_SERVICE) as LocationManager

fun Context.openAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", packageName, null)
    }
    startActivity(intent)
}

inline fun Context.drawableCompat(id: Int) = AppCompatResources.getDrawable(this, id)