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

context(Project)
internal val libs
    get() = the<LibrariesForLibs>()

context(Project)
internal fun libraryExtension(action: LibraryExtension.() -> Unit) =
    extensions.configure<LibraryExtension>(action)

context(Project)
internal fun kmpExtension(action: KotlinMultiplatformExtension.() -> Unit) =
    extensions.configure<KotlinMultiplatformExtension>(action)

context(Project)
internal fun applicationExtension(action: ApplicationExtension.() -> Unit) =
    extensions.configure<ApplicationExtension>(action)

context(Project)
internal fun kotlinExtension(action: KotlinProjectExtension.() -> Unit) =
    extensions.configure<KotlinProjectExtension>(action)

context(Project)
fun composeCompiler(block: ComposeCompilerGradlePluginExtension.() -> Unit) =
    extensions.configure<ComposeCompilerGradlePluginExtension>(block)

context(Project)
internal fun configureKotlinAndroidToolchain() {
    extensions.configure<KotlinAndroidProjectExtension> {
        jvmToolchain(17)
    }
}