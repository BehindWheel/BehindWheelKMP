package com.egoriku.grodnoroads.util

import android.content.Context
import android.util.LruCache
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MarkerCache(private val context: Context) {

    private val maxSize = (Runtime.getRuntime().maxMemory() / 1024 / 8).toInt()
    private val lruCache = LruCache<Int, BitmapDescriptor>(maxSize)

    fun getVector(id: Int): BitmapDescriptor {
        return when (val cachedBitmap = lruCache.get(id)) {
            null -> {
                val bitmap = requireNotNull(AppCompatResources.getDrawable(context, id)).toBitmap()

                BitmapDescriptorFactory.fromBitmap(bitmap).also { bitmapDescriptor ->
                    lruCache.put(id, bitmapDescriptor)
                }
            }
            else -> cachedBitmap
        }
    }
}