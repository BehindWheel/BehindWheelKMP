import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

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

tasks {
    withType<DependencyUpdatesTask> {
        rejectVersionIf {
            isNonStable(candidate.version)
        }
    }

    registering(Delete::class) {
        delete(buildDir)
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

//assembleRelease -Pgrodnoroads.enableComposeCompilerReports=true
subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            if (project.findProperty("grodnoroads.enableComposeCompilerReports") == "true") {
                freeCompilerArgs = freeCompilerArgs +
                        "-P=plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${project.buildDir.absolutePath}/compose_metrics" +
                        "-P=plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${project.buildDir.absolutePath}/compose_metrics"
            }
        }
    }
}