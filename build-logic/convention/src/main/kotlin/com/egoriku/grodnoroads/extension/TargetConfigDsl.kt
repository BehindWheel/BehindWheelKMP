package com.egoriku.grodnoroads.extension

import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.codingfeline.buildkonfig.gradle.TargetConfigDsl
import org.gradle.api.NamedDomainObjectContainer

fun TargetConfigDsl.buildConfigField(
    name: String,
    value: String
) {
    buildConfigField(type = STRING, name = name, value = value, const = true)
}

fun NamedDomainObjectContainer<TargetConfigDsl>.ios(block: TargetConfigDsl.() -> Unit) {
    listOf("iosArm64", "iosSimulatorArm64", "iosX64").forEach {
        create(it, block)
    }
}

fun NamedDomainObjectContainer<TargetConfigDsl>.android(block: TargetConfigDsl.() -> Unit) {
    create("android", block)
}
