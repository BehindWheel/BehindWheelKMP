package com.egoriku.grodnoroads.shared.audioplayer.util

import android.media.audiofx.AudioEffect
import com.egoriku.grodnoroads.logger.logD

internal object AudioEffectUtil {

    fun isLoudnessEnhancerAvailable(): Boolean {
        try {
            val queryEffects = AudioEffect.queryEffects()
            if (queryEffects != null) {
                for (descriptor in queryEffects) {
                    if (descriptor.type == AudioEffect.EFFECT_TYPE_LOUDNESS_ENHANCER) {
                        return true
                    }
                }
            }
        } catch (throwable: Throwable) {
            logD("AudioEffectUtil: $throwable")
        }
        return false
    }
}
