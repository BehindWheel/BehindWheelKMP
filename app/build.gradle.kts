plugins {
    id("com.android.application")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    kotlin("android")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.egoriku.grodnoroads"
        minSdk = 21
        targetSdk = 32
        versionCode = 119
        versionName = "1.0.19"
        resourceConfigurations + listOf("en", "ru")
    }

    signingConfigs {
        debug {
            storeFile = keyStoreFile("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }

        release {
            storeFile = keyStoreFile("keystore.jks", "debug.keystore")
            storePassword = System.getenv("KEY_STORE_PASSWORD") ?: "android"
            keyAlias = System.getenv("KEY_ALIAS") ?: "androiddebugkey"
            keyPassword = System.getenv("KEY_PASSWORD") ?: "android"
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isMinifyEnabled = false
        }

        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(
                "proguard-rules.pro",
                getDefaultProguardFile("proguard-android-optimize.txt")
            )
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:30.1.0"))

    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-database-ktx")

    implementation("com.google.maps.android:android-maps-utils:2.3.0")
    implementation("com.google.maps.android:maps-ktx:3.4.0")
    implementation(libs.maps.compose)

    implementation("com.google.android.gms:play-services-maps:18.0.2")
    implementation("com.google.android.gms:play-services-location:20.0.0")
    implementation("com.google.android.material:material:1.6.1")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.24.12-rc")
    implementation("com.google.accompanist:accompanist-permissions:0.24.12-rc")

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.browser)
    implementation(libs.activity.compose)
    implementation(libs.androidx.core)
    implementation(libs.androidx.datastore)

    implementation(libs.coroutines.play.services)

    implementation("io.insert-koin:koin-android:3.2.0")
    implementation("io.insert-koin:koin-androidx-compose:3.2.0")

    implementation("com.arkivanov.decompose:decompose:0.6.0")
    implementation("com.arkivanov.decompose:extensions-compose-jetpack:0.6.0")

    implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:3.0.0-beta02")
    implementation("com.arkivanov.mvikotlin:mvikotlin-main:3.0.0-beta02")
    implementation("com.arkivanov.mvikotlin:mvikotlin:3.0.0-beta02")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.6.21")
}

secrets {
    propertiesFileName = "secrets.properties"
}

fun keyStoreFile(vararg fileNames: String): File? {
    for (path in fileNames) {
        val file = project.file(path)

        if (file.exists()) {
            return file
        }
    }

    return null
}