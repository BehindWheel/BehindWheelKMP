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
    // https://stackoverflow.com/a/75032841
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Grodno-Roads"

include(":app")
include(":app-kmm")

include(":features:map:map-data")
include(":features:map:map-domain")
include(":features:map:map-ui")

include(":features:settings")

include(":features:setting:alerts")
include(":features:setting:appearance")
include(":features:setting:faq")
include(":features:setting:map")
include(":features:setting:whatsnew")

include(":compose:foundation:core")
include(":compose:foundation:preview")
include(":compose:foundation:theme")
include(":compose:foundation:uikit")
include(":compose:location-requester")
include(":compose:maps:compose")
include(":compose:maps:core")
include(":compose:snackbar")

include(":libraries:analytics")
include(":libraries:audioplayer")
include(":libraries:crashlytics")
include(":libraries:extensions")
include(":libraries:foundation")
include(":libraries:location")
include(":libraries:resources")

include(":shared:appSettings")
include(":shared:appComponent")
include(":test_shared")

include(":kmp:features:root")
