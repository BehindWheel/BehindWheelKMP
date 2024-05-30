enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")

    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    // https://stackoverflow.com/a/75032841
    @Suppress("UnstableApiUsage")
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Grodno-Roads"

include(":app:android")
include(":app:ui-demo")

include(":compose:location-requester")
include(":compose:maps:compose")

include(":kmp:compose:common-ui")
include(":kmp:compose:foundation:core")
include(":kmp:compose:foundation:preview")
include(":kmp:compose:foundation:theme")
include(":kmp:compose:foundation:uikit")
include(":kmp:compose:resources")
include(":kmp:compose:snackbar")

include(":kmp:features:root")
include(":kmp:features:onboarding")
include(":kmp:features:mainflow")

include(":kmp:features:guidance")
include(":kmp:features:eventReporting")
include(":kmp:features:specialEventReminder")
include(":kmp:features:quickSettings")

include(":kmp:features:tabs")

include(":kmp:features:appSettings")
include(":kmp:features:settings:alerts")
include(":kmp:features:settings:appearance")
include(":kmp:features:settings:changelog")
include(":kmp:features:settings:faq")
include(":kmp:features:settings:map")

include(":kmp:libraries:coroutines")
include(":kmp:libraries:datastore")
include(":kmp:libraries:extensions")
include(":kmp:libraries:location")
include(":kmp:libraries:logger")
include(":kmp:libraries:uuid")

include(":kmp:shared:audioplayer")
include(":kmp:shared:analytics")
include(":kmp:shared:components")
include(":kmp:shared:crashlytics")
include(":kmp:shared:geolocation")
include(":kmp:shared:models")
include(":kmp:shared:persistent")
include(":kmp:shared:resources")
