package com.egoriku.grodnoroads.internal

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.Action
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun KotlinMultiplatformExtension.android(
    configure: Action<KotlinMultiplatformAndroidLibraryTarget>
) = extensions.configure("android", configure)
