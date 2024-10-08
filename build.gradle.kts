import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.buildkonfig) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.cocoapods) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.moko.resources) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.spotless) apply false
}

allprojects {
    plugins.apply(
        rootProject.libs.plugins.spotless
            .get()
            .pluginId
    )

    extensions.configure<SpotlessExtension> {
        kotlin {
            target("src/**/*.kt")
            targetExclude("src/test/resources/**")
            ktlint(libs.ktlint.get().version)
                .editorConfigOverride(
                    mapOf(
                        "ktlint_compose_lambda-param-event-trailing" to "disabled",
                        "ktlint_standard_function-expression-body" to "disabled",
                        "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
                        "ktlint_compose_compositionlocal-allowlist" to "disabled",
                        "ktlint_standard_backing-property-naming" to "disabled",
                        "compose_treat_as_lambda" to false,
                        "compose_disallow_material2" to true,
                        "compose_preview_naming_enabled" to true,
                        "compose_preview_naming_strategy" to "suffix"
                    )
                ).customRuleSets(
                    listOf(
                        libs.compose.rules
                            .get()
                            .toString()
                    )
                )
        }
        kotlinGradle {
            ktlint(libs.ktlint.get().version)
        }
    }
}

tasks {
    registering(Delete::class) {
        delete(layout.buildDirectory)
    }
}
