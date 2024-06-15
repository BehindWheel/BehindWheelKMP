package com.egoriku.grodnoroads.internal

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal val Project.libs
    get() = the<LibrariesForLibs>()

internal fun Project.libraryExtension(action: LibraryExtension.() -> Unit) =
    extensions.configure<LibraryExtension>(action)

internal fun Project.kmpExtension(action: KotlinMultiplatformExtension.() -> Unit) =
    extensions.configure<KotlinMultiplatformExtension>(action)

internal fun Project.applicationExtension(action: ApplicationExtension.() -> Unit) =
    extensions.configure<ApplicationExtension>(action)

internal fun Project.kotlinExtension(action: KotlinProjectExtension.() -> Unit) =
    extensions.configure<KotlinProjectExtension>(action)

fun Project.composeCompiler(block: ComposeCompilerGradlePluginExtension.() -> Unit) =
    extensions.configure<ComposeCompilerGradlePluginExtension>(block)

internal fun Project.configureKotlinAndroidToolchain() {
    extensions.configure<KotlinAndroidProjectExtension> {
        jvmToolchain(17)
    }
}