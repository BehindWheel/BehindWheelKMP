package com.egoriku.grodnoroads.extensions

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object Uuid {

    @OptIn(ExperimentalUuidApi::class)
    fun random() = Uuid.random().toString()
}
