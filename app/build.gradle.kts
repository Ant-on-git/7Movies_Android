import java.util.Properties

val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

val apiKey = localProperties.getProperty("KINOPOISKUNOFF_API_KEY") ?: ""


plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.a7movies"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.a7movies"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "KINOPOISKUNOFF_API_KEY", "\"$apiKey\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        buildConfig = true
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

    // Retrofit (работа с интернетом)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)

    // RxJava (работа с потоками)
    implementation(libs.rxJava)
    implementation(libs.rxJava.android)

    // адаптер для работы retrofit с RxJava
    implementation(libs.retrofit.adapter.rxjava)

    // Room (бд)
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    implementation(libs.room.rxjava3)

    // Glide    (изображения)
    implementation(libs.glide)
}