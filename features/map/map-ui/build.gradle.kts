plugins {
    id("grodnoroads.library")
    id("grodnoroads.library.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.map"
}

dependencies {
    implementation(projects.features.map.mapDomain)
}
