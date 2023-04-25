import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import java.util.Locale

// Remove after https://github.com/gradle/gradle/issues/22797 (~gradle 8.1)
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.gradle.dependency.check)
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.secrets) apply false
}

tasks {
    registering(Delete::class) {
        delete(buildDir)
    }
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
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any {
        version.uppercase(Locale.getDefault()).contains(it)
    }
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