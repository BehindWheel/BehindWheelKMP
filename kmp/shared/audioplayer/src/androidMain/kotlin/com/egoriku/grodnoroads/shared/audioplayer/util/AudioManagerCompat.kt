package com.egoriku.grodnoroads.shared.audioplayer.util

import android.content.Context
import android.media.AudioManager
import androidx.annotation.OptIn
import androidx.media3.common.audio.AudioManagerCompat
import androidx.media3.common.util.UnstableApi

internal val Context.audioManagerCompat: AudioManager
    @OptIn(UnstableApi::class)
    get() = AudioManagerCompat.getAudioManager(this)

@OptIn(UnstableApi::class)
internal fun AudioManager.getStreamVolumeCompat(streamType: Int): Int {
    return AudioManagerCompat.getStreamVolume(this, streamType)
}

@OptIn(UnstableApi::class)
internal fun AudioManager.getStreamMaxVolumeCompat(streamType: Int): Int {
    return AudioManagerCompat.getStreamMaxVolume(this, streamType)
}
