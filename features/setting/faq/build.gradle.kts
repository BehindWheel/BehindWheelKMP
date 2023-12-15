plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.setting.faq"
}

dependencies {
    implementation(projects.kmp.libraries.extensions)

    implementation(projects.compose.commonUi)

    implementation(projects.libraries.crashlytics)
    implementation(projects.libraries.extensions)
    implementation(projects.libraries.resources)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)

    implementation(libs.decompose)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)

    implementation(libs.mvikotlin.extensions)
    implementation(libs.mvikotlin.main)
    implementation(libs.mvikotlin)
}