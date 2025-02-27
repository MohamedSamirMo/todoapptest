plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.task.apptest"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.task.apptest"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //shimmer
    implementation (libs.shimmer)


    // retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    implementation (libs.androidx.room.runtime)

    implementation(libs.androidx.room.runtime)
    // To use Kotlin annotation processing tool (kapt)
    implementation (libs.androidx.cardview)

    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)

    //Kotlin Coroutines
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)
    // sdp
    implementation(libs.sdp.android)

    // ViewModel
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    // LiveData
    implementation (libs.androidx.lifecycle.livedata.ktx)
    //dagger Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    // fragment Ktx
    implementation (libs.androidx.fragment.ktx)
    // navigation
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)
    //ssp
    implementation(libs.ssp.android)
    //OKHttp
    implementation (libs.logging.interceptor)
    ksp(libs.androidx.room.compiler)

}
kapt{
    correctErrorTypes = true

}