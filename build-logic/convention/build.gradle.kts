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
        register("grodnoroadsLibrary") {
            id = "grodnoroads.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("grodnoroadsLibraryCompose") {
            id = "grodnoroads.library.compose"
            implementationClass = "AndroidLibraryComposePlugin"
        }
    }
}