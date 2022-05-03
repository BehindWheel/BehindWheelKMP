package com.egoriku.grodnoroads.util

import android.content.Context
import com.egoriku.grodnoroads.R

interface ResourceProvider {

    val locationDisabled: String
}

internal class ResourceProviderImpl(private val context: Context) : ResourceProvider {

    override val locationDisabled: String
        get() = context.getString(R.string.toast_location_disabled)
}

