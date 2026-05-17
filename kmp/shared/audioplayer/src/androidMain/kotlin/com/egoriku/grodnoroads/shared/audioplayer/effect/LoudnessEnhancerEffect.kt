package com.egoriku.grodnoroads.shared.audioplayer.effect

import android.media.audiofx.LoudnessEnhancer
import com.egoriku.grodnoroads.logger.logD

internal class LoudnessEnhancerEffect(audioSessionId: Int) {

    private val enhancer: LoudnessEnhancer? = try {
        LoudnessEnhancer(audioSessionId).apply { enabled = true }
    } catch (t: Throwable) {
        logD("LoudnessEnhancer init failed: ${t.message}")
        null
    }

    fun setTargetGain(gainMb: Int) {
        try {
            enhancer?.setTargetGain(gainMb)
        } catch (t: Throwable) {
            logD("LoudnessEnhancer setTargetGain failed: ${t.message}")
        }
    }

    fun release() {
        try {
            enhancer?.release()
        } catch (t: Throwable) {
            logD("LoudnessEnhancer release failed: ${t.message}")
        }
    }
}
