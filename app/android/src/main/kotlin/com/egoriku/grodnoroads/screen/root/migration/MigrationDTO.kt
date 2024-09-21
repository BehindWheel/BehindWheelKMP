package com.egoriku.grodnoroads.screen.root.migration

import androidx.annotation.Keep
import com.google.firebase.database.PropertyName

@Keep
class MigrationDTO(

    @PropertyName("enabled")
    @JvmField
    val enabled: Boolean = false,

    @PropertyName("link")
    @JvmField
    val link: String = "",

    @PropertyName("newPackage")
    @JvmField
    val newPackage: String = ""
)