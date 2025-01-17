package com.egoriku.grodnoroads.logger

actual fun logD(message: String) {
    println("$TAG: $message")
}
