package com.egoriku.grodnoroads.test_shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform