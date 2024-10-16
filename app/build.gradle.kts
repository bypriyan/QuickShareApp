plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
//    id("kotlin-kapt")
//    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.multishare"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.multishare"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

//    // hilt
//    implementation(libs.hilt.android)
//    kapt(libs.hilt.android.compiler)
    // Material Design 3
    implementation(libs.material3)
    implementation(libs.androidx.compose.material3.material3)
    implementation(libs.androidx.material)

    //splash screen api
    implementation (libs.androidx.core.splashscreen)
    //multidex
    implementation (libs.androidx.annotation)
    implementation (libs.annotations)
    implementation (libs.androidx.multidex)
    //lottie anim
    implementation ("com.airbnb.android:lottie-compose:6.5.2")
    //coroutines
    implementation(libs.kotlinx.coroutines.android)

    //mvvm
    implementation (libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation (libs.lifecycle.viewmodel.compose)
    // Lifecycles only (without ViewModel or LiveData)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)

    // LiveData
    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.androidx.core.ktx)
    //coil
    implementation(libs.coil.compose)
    //constraint layout
    implementation (libs.androidx.constraintlayout.compose)

    implementation (libs.kotlinx.coroutines.play.services)
    //navigation
    val nav_version = "2.8.2"
    implementation("androidx.navigation:navigation-compose:$nav_version")
    //
    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")

}
//
//kapt {
//    correctErrorTypes = true
//}