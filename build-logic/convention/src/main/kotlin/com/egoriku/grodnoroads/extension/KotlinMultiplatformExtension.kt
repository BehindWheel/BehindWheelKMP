package com.egoriku.grodnoroads.extension

import com.egoriku.grodnoroads.internal.android
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun KotlinMultiplatformExtension.configureTargets(namespace: String) {
    android {
        this.namespace = namespace
    }

    iosArm64()
    iosSimulatorArm64()
}
