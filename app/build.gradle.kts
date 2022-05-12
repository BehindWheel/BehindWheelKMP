plugins {
    id("com.android.application")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("plugin.serialization")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.egoriku.grodnoroads"
        minSdk = 21
        targetSdk = 32
        versionCode = 106
        versionName = "1.0.6"
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
    implementation(projects.library.retrosheetKmm)

    implementation(platform("com.google.firebase:firebase-bom:30.0.0"))

    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-database-ktx")

    implementation("com.google.maps.android:android-maps-utils:2.3.0")
    implementation("com.google.maps.android:maps-ktx:3.4.0")
    implementation("com.google.maps.android:maps-compose:2.1.1")

    implementation("com.google.android.gms:play-services-maps:18.0.2")
    implementation("com.google.android.gms:play-services-location:19.0.1")

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.24.8-beta")
    implementation("com.google.accompanist:accompanist-permissions:0.24.8-beta")

    implementation(libs.androidx.browser)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")

    implementation("com.github.theapache64:retrosheet:2.0.0-beta03")

    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")

    implementation("com.google.android.material:material:1.6.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.moshi:moshi:1.13.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")

    implementation("io.insert-koin:koin-android:3.2.0")
    implementation("io.insert-koin:koin-androidx-compose:3.2.0")

    implementation("com.arkivanov.decompose:decompose:0.6.0")
    implementation("com.arkivanov.decompose:extensions-compose-jetpack:0.6.0")

    implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:3.0.0-beta02")
    implementation("com.arkivanov.mvikotlin:mvikotlin-main:3.0.0-beta02")
    implementation("com.arkivanov.mvikotlin:mvikotlin:3.0.0-beta02")

    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.contentnegotiation)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.serialization.json)
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