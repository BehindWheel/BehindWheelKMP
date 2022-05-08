apply(plugin = "com.github.ben-manes.versions")

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.gradle.plugin.buildtools)
        classpath(libs.gradle.plugin.crashlytics)
        classpath(libs.gradle.plugin.googleservices)
        classpath(libs.gradle.plugin.kotlin)
        classpath(libs.gradle.plugin.ksp)
        classpath(libs.gradle.plugin.versioncheck)
        classpath(libs.gradle.plugin.secrets)
        classpath(libs.gradle.plugin.serialization)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

tasks {
    registering(Delete::class) {
        delete(buildDir)
    }
}
