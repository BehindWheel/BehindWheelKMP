plugins {
    `kotlin-dsl`
}

group = "com.egoriku.grodnoroads"

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)

    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("grodnoroads.library") {
            id = "grodnoroads.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("grodnoroads.compose") {
            id = "grodnoroads.compose"
            implementationClass = "AndroidComposePlugin"
        }
        register("grodnoroads.application") {
            id = "grodnoroads.application"
            implementationClass = "AndroidApplicationPlugin"
        }
    }
}