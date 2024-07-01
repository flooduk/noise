plugins {
    id("com.android.application")
}

android {
    namespace = "uk.flood.noise"
    compileSdk = 34

    defaultConfig {
        applicationId = "uk.flood.noise"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = true
            isProfileable = false
            isDebuggable = false
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
