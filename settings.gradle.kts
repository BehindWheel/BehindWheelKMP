enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        includeBuild("build-logic")
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Grodno-Roads"

include(":app:android")
include(":app:ui-demo")

include(":features:map:map-data")
include(":features:map:map-domain")
include(":features:map:map-ui")

include(":features:eventReporting")

include(":features:settings")

include(":features:setting:alerts")
include(":features:setting:appearance")
include(":features:setting:faq")
include(":features:setting:map")
include(":features:setting:changelog")

include(":compose:foundation:core")
include(":compose:foundation:preview")
include(":compose:foundation:theme")
include(":compose:foundation:uikit")

include(":compose:common-ui")
include(":compose:location-requester")
include(":compose:maps:compose")
include(":compose:maps:core")
include(":compose:snackbar")

include(":libraries:analytics")
include(":libraries:audioplayer")
include(":libraries:crashlytics")
include(":libraries:extensions")
include(":libraries:location")
include(":libraries:resources")

include(":shared:appComponent")
include(":shared:appSettings")
include(":shared:coreModels")
