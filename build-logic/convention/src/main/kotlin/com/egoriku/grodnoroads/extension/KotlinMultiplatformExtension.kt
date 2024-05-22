package com.egoriku.grodnoroads.extension

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework

fun KotlinMultiplatformExtension.applyTargets() {
    androidTarget()
    iosTarget()
}

fun KotlinMultiplatformExtension.iosTarget() {
    iosX64()
    iosArm64()
    iosSimulatorArm64()
}

fun KotlinMultiplatformExtension.setupIosStaticFramework(
    name: String,
    configure: Framework.() -> Unit
) {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = name.toFrameworkName()
            isStatic = true
            configure()
        }
    }
}

private fun String.toFrameworkName() = split("_")
    .joinToString(
        separator = "",
        transform = {
            it.replaceFirstChar(Char::titlecase)
        }
    )

fun KotlinMultiplatformExtension.commonDependencies(
    configure: KotlinDependencyHandler.() -> Unit,
) = sourceSets.commonMain.dependencies { configure() }

fun KotlinMultiplatformExtension.commonTestDependencies(
    configure: KotlinDependencyHandler.() -> Unit,
) = sourceSets.commonTest.dependencies { configure() }

fun KotlinMultiplatformExtension.androidDependencies(
    configure: KotlinDependencyHandler.() -> Unit,
) = sourceSets.androidMain.dependencies { configure() }

fun KotlinMultiplatformExtension.iosDependencies(
    configure: KotlinDependencyHandler.() -> Unit,
) = sourceSets.iosMain.dependencies { configure() }