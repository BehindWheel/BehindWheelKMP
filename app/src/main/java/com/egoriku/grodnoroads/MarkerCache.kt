package com.egoriku.grodnoroads

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import androidx.annotation.DrawableRes
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MarkerCache(private val context: Context) {

    private val maxSize = (Runtime.getRuntime().maxMemory() / 1024 / 8).toInt()
    private val lruCache = LruCache<Int, BitmapDescriptor>(maxSize)

    fun getOrPut(@DrawableRes id: Int): BitmapDescriptor {
        return when (val cachedBitmap = lruCache.get(id)) {
            null -> {
                BitmapDescriptorFactory.fromBitmap(
                    smallIcon(id)
                ).also { bitmapDescriptor ->
                    lruCache.put(id, bitmapDescriptor)
                }
            }
            else -> cachedBitmap
        }
    }

    private fun smallIcon(id: Int): Bitmap {
        val height = 80
        val width = 80
        val bitmap = BitmapFactory.decodeResource(context.resources, id)
        return Bitmap.createScaledBitmap(bitmap, width, height, false)
    }
}