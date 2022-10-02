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
    compileSdk = 33

    defaultConfig {
        applicationId = "com.egoriku.grodnoroads"
        minSdk = 21
        targetSdk = 33
        versionCode = 122
        versionName = "1.0.22"
        resourceConfigurations += listOf("en", "ru", "be-rBY")
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
        }

        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(
                "proguard-rules.pro",
                getDefaultProguardFile("proguard-android-optimize.txt")
            )
            isShrinkResources = true
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.egoriku.grodnoroads"
}

dependencies {
    implementation(projects.libraries.extensions)

    implementation(projects.features.map.mapData)
    implementation(projects.features.map.mapDomain)
    implementation(projects.features.map.mapUi)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)

    implementation(libs.maps.utils)
    implementation(libs.maps)
    implementation(libs.maps.compose)

    implementation(libs.gms.location)
    implementation("com.google.android.material:material:1.6.1")

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.permissions)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.core)
    implementation(libs.androidx.datastore)

    implementation(libs.coroutines.playservices)

    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(libs.decompose)
    implementation(libs.decompose.compose.jetpack)

    implementation(libs.mvikotlin.extensions)
    implementation(libs.mvikotlin.main)
    implementation(libs.mvikotlin)
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