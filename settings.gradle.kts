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

include(":app:android")
include(":app:ui-demo")

include(":features:map:map-data")
include(":features:map:map-domain")
include(":features:map:map-ui")

include(":features:setting:alerts")
include(":features:setting:appearance")
include(":features:setting:map")

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
include(":libraries:extensions")
include(":libraries:location")
include(":libraries:resources")

include(":shared:appSettings")

include(":kmp:features:root")
include(":kmp:features:onboarding")
include(":kmp:features:mainflow")
include(":kmp:features:tabs")
include(":kmp:features:appSettings")
include(":kmp:features:settings:changelog")
include(":kmp:features:settings:faq")

include(":kmp:libraries:crashlytics")
include(":kmp:libraries:datastore")
include(":kmp:libraries:extensions")
include(":kmp:libraries:logger")

include(":kmp:shared:components")
include(":kmp:shared:models")
