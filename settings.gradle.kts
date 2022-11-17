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

include(":features:settings:alerts")
include(":features:settings:appearance")
include(":features:settings:faq")
include(":features:settings:map")
include(":features:settings:root")
include(":features:settings:whatsnew")

include(":libraries:analytics")
include(":libraries:crashlytics")
include(":libraries:extensions")
include(":libraries:foundation")
include(":libraries:location")
include(":libraries:resources")

include(":shared:appSettings")
include(":shared:appComponent")