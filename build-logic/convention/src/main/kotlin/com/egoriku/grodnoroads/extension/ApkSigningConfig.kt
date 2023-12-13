package com.egoriku.grodnoroads.extension

import com.android.build.api.dsl.ApkSigningConfig
import org.gradle.api.Project
import java.io.File
import org.gradle.api.NamedDomainObjectContainer as Container

@JvmName("debugSigningConfig")
fun Container<out ApkSigningConfig>.debug(setup: ApkSigningConfig.() -> Unit) =
    get("debug", setup)

@JvmName("releaseSigningConfig")
fun Container<out ApkSigningConfig>.release(setup: ApkSigningConfig.() -> Unit) =
    get("release", setup)

@JvmName("signingConfig")
private fun Container<out ApkSigningConfig>.get(
    name: String,
    block: ApkSigningConfig.() -> Unit
) {
    when (val task = findByName(name)) {
        null -> create(name)
        else -> task
    }.block()
}

fun Project.loadKeystore(vararg fileNames: String): File? {
    for (path in fileNames) {
        val file = project.file(path)

        if (file.exists()) {
            return file
        }
    }

    return null
}