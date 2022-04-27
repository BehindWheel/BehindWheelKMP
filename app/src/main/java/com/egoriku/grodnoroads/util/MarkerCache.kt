package com.egoriku.grodnoroads.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import androidx.annotation.DrawableRes
import androidx.core.graphics.scale
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MarkerCache(private val context: Context) {

    private val maxSize = (Runtime.getRuntime().maxMemory() / 1024 / 8).toInt()
    private val lruCache = LruCache<Int, BitmapDescriptor>(maxSize)

    fun getOrPut(@DrawableRes id: Int): BitmapDescriptor {
        return when (val cachedBitmap = lruCache.get(id)) {
            null -> {
                BitmapDescriptorFactory.fromResource(id).also { bitmapDescriptor ->
                    lruCache.put(id, bitmapDescriptor)
                }
            }
            else -> cachedBitmap
        }
    }

    fun getOrPut(@DrawableRes id: Int, size: Int): BitmapDescriptor {
        return when (val cachedBitmap = lruCache.get(id)) {
            null -> {
                val resizedIcon = resize(id = id, size = size)

                BitmapDescriptorFactory.fromBitmap(resizedIcon).also { bitmapDescriptor ->
                    lruCache.put(id, bitmapDescriptor)
                }
            }
            else -> cachedBitmap
        }
    }

    private fun resize(@DrawableRes id: Int, size: Int): Bitmap {
        return BitmapFactory.decodeResource(context.resources, id)
            .scale(
                width = size,
                height = size,
                filter = false
            )
    }
}