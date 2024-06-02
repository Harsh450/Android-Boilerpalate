@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.hilt)
    alias(libs.plugins.androidx.navigation.safeargs)
}

android {
    namespace = "com.example.androidboilerplate"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.androidboilerplate"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField(
                type = "String",
                name = "API_BASE_URL",
                value = "\"https://rss.applemarketingtools.com/api/v2\""
            )
        }
        release {
            buildConfigField(
                type = "String",
                name = "API_BASE_URL",
                value = "\"https://rss.applemarketingtools.com/api/v2\""
            )
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Room
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)

    //Ktor
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.serialization)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.logging.jvm)

    //Coroutine
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //Livedata
    implementation(libs.androidx.lifecycle.livedata.ktx)

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //Navigation Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //Glide
    implementation(libs.glide)
    kapt(libs.compiler)

    //App Update Check
    implementation(libs.app.update.ktx)

    //Ssp and Sdp
    implementation(libs.sdp.android)
    implementation(libs.ssp.android)

    //Gson
    implementation(libs.gson)

    //Data Store
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.preference.ktx)

    //Firebase
    implementation(libs.firebase.messaging.ktx)

    //Google Play Service Location
    implementation(libs.play.services.location)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Navigation Feature module Support
    implementation(libs.androidx.navigation.dynamic.features.fragment)

    // Dependency required for API desugaring.
    coreLibraryDesugaring(libs.desugar.jdk.libs.nio)
}