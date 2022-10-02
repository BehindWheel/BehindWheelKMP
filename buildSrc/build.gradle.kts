plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    compileOnly(gradleApi())
    compileOnly(localGroovy())

    compileOnly(libs.gradle.plugin.buildtools)
    compileOnly(libs.gradle.plugin.kotlin)
}