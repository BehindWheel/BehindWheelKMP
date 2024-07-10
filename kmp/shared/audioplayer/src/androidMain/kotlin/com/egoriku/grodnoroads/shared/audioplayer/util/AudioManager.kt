package com.egoriku.grodnoroads.shared.audioplayer.util

import android.content.Context
import android.media.AudioManager

internal val Context.audioManager: AudioManager
    get() = getSystemService(Context.AUDIO_SERVICE) as AudioManager
