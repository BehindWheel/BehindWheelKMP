package com.egoriku.grodnoroads.extension

import android
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun KotlinMultiplatformExtension.configureTargets(namespace: String) {
    android {
        this.namespace = namespace
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()
}
