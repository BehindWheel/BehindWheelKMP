plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())

    implementation(libs.gradle.plugin.buildtools)
    implementation(libs.gradle.plugin.kotlin)
}