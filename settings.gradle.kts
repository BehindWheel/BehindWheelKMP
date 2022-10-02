enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        includeBuild("build-logic")
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "Grodno-Roads"

include(":app")

include(":features:map:map-data")
include(":features:map:map-domain")
include(":features:map:map-ui")

include(":libraries:extensions")