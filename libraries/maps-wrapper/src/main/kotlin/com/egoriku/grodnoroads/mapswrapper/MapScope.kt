package com.egoriku.grodnoroads.mapswrapper

import android.util.Log
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Immutable
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.ktx.addMarker

@LayoutScopeMarker
@Immutable
interface MapScope {
    fun attach()
    fun detach()

    fun addMarker(
        position: LatLng,
        icon: BitmapDescriptor? = null,
        onClick: () -> Unit = { },
        zIndex: Float = 0.0f,
    )

    fun removeMarker(position: LatLng)
}

internal class MapScopeInstance(private val map: GoogleMap) : MapScope {

    private val markers = mutableListOf<InternalMarker>()

    override fun attach() {
        Log.d("kek", "attach: ")
        map.setOnMarkerClickListener { marker ->
            when (val internalMarker = markers.find { it.marker == marker }) {
                null -> false
                else -> consume {
                    internalMarker.onClick()
                }
            }
        }
    }

    override fun detach() {
        Log.d("kek", "detach: ")
        map.setOnMarkerClickListener(null)
        markers.clear()
    }

    override fun addMarker(
        position: LatLng,
        icon: BitmapDescriptor?,
        onClick: () -> Unit,
        zIndex: Float
    ) {
        val internalMarker = markers.find { it.marker.position == position }
        if (internalMarker == null) {
            val marker = map.addMarker {
                position(position)
                icon(icon)
                zIndex(zIndex)
            }
            if (marker != null) {
                Log.d("kek", "add marker: ")
                markers.add(InternalMarker(marker, onClick))
            }
        } else {
            Log.d("kek", "update marker: ")
            internalMarker.marker.apply {
                this.position = position
                this.zIndex = zIndex
                setIcon(icon)
            }
        }
    }

    override fun removeMarker(position: LatLng) {
        Log.d("kek", "removeMarker")
        val internalMarker = markers.find { it.marker.position == position } ?: return
        internalMarker.marker.remove()
        markers.remove(internalMarker)
    }
}

data class InternalMarker(
    val marker: Marker,
    val onClick: () -> Unit = { },
)

inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}