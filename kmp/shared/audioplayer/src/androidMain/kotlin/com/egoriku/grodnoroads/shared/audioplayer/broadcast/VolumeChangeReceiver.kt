package com.egoriku.grodnoroads.shared.audioplayer.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager

const val VOLUME_CHANGE_ACTION = "android.media.VOLUME_CHANGED_ACTION"
private const val EXTRA_VOLUME_STREAM_VALUE = "android.media.EXTRA_VOLUME_STREAM_VALUE"
private const val EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE"

class VolumeChangeReceiver(private val onVolumeChange: (Int) -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == VOLUME_CHANGE_ACTION) {
            val volume = intent.getIntExtra(EXTRA_VOLUME_STREAM_VALUE, 0)
            val streamType = intent.getIntExtra(EXTRA_VOLUME_STREAM_TYPE, 0)

            if (streamType == AudioManager.STREAM_MUSIC) onVolumeChange(volume)
        }
    }
}
