plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.jhatpatdownload"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.jhatpatdownload"
        minSdk = 26
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

        buildFeatures {
            viewBinding = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit and Gson
    implementation (libs.retrofit.v300)
    implementation (libs.converter.gson.v300)

    // Lifecycle components
    implementation (libs.lifecycle.viewmodel.ktx)
    implementation (libs.lifecycle.livedata.ktx)

    // image loading
    implementation (libs.glide)
    annotationProcessor (libs.compiler)

    // bottom nav
    implementation  (libs.bubbletabbar)

}