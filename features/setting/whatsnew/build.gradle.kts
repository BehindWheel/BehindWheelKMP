plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.setting.whatsnew"
}

dependencies {
    implementation(projects.compose.foundation.preview)
    implementation(projects.compose.foundation.theme)
    implementation(projects.compose.foundation.uikit)

    implementation(projects.compose.commonUi)

    implementation(projects.libraries.crashlytics)
    implementation(projects.libraries.extensions)
    implementation(projects.libraries.resources)

    implementation(projects.shared.appSettings)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)

    implementation(libs.decompose)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)

    implementation(libs.mvikotlin)
    implementation(libs.mvikotlin.extensions)
    implementation(libs.mvikotlin.main)
}