package com.egoriku.grodnoroads.extension

import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal fun KotlinProjectExtension.compilerOptions(block: KotlinCommonCompilerOptions.() -> Unit) {
    when (this) {
        is KotlinJvmProjectExtension -> compilerOptions.block()
        is KotlinAndroidProjectExtension -> compilerOptions.block()
        is KotlinMultiplatformExtension -> {
            targets.all {
                compilations.all {
                    compileTaskProvider.configure {
                        compilerOptions {
                            block()
                        }
                    }
                }
            }
        }
        else -> error("Unknown kotlin extension $this")
    }
}