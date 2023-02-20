plugins {
    `kotlin-dsl`
}

group = "com.epmedu.animeal"

dependencies {
    compileOnly(libs.gradle.plugin.buildtools)
    compileOnly(libs.gradle.plugin.kotlin)

    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("grodnoroads.library") {
            id = "grodnoroads.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("grodnoroads.library.compose") {
            id = "grodnoroads.library.compose"
            implementationClass = "AndroidLibraryComposePlugin"
        }
        register("grodnoroads.application") {
            id = "grodnoroads.application"
            implementationClass = "AndroidApplicationPlugin"
        }
    }
}