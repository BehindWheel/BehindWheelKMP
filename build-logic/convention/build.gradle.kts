plugins {
    `kotlin-dsl`
}

group = "com.egoriku.grodnoroads"

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.compiler.plugin)
    compileOnly(libs.buildkonfig.compiler)
    compileOnly(libs.buildkonfig.gradle)

    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("grodnoroads.kmp.library") {
            id = "grodnoroads.kmp.library"
            implementationClass = "AndroidKmpLibraryPlugin"
        }
        register("grodnoroads.kmp.compose") {
            id = "grodnoroads.kmp.compose"
            implementationClass = "KmpComposePlugin"
        }
        register("grodnoroads.application") {
            id = "grodnoroads.application"
            implementationClass = "AndroidApplicationPlugin"
        }
    }
}
