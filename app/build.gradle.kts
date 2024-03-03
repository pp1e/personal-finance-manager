plugins {
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.personalfinancemanager"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.personalfinancemanager"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

//        ksp {
//            arguments {arg("room.schemaLocation", "$projectDir/schemas")}
//        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.coreKtx)
    implementation(libs.runtimeCtx)
    implementation(libs.activityCompose)
    implementation(platform(libs.composeBom))
    implementation(libs.bundles.compose)
    implementation(libs.bundles.decompose)
    implementation(libs.bundles.mvikotlin)
    implementation(libs.bundles.reactive)
    implementation(libs.bundles.room)

    annotationProcessor(libs.roomCompiler)

    ksp(libs.roomCompiler)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidJunit)
    androidTestImplementation(libs.espressoCore)
    androidTestImplementation(platform(libs.composeBom))
    androidTestImplementation(libs.composeJunit)

    debugImplementation(libs.composeUiTooling)
    debugImplementation(libs.composeUiTestManifest)
}