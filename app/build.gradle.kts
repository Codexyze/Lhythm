plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.0.21"
    id("kotlin-kapt")
}

android {

//    lint {
//        baseline = file("lint-baseline.xml")
//    }
    namespace = "com.example.lhythm"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.lhythm"
        minSdk = 24
        targetSdk = 36
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15" //add this
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
    implementation(libs.androidx.media3.transformer)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //val nav_version = "2.8.3"
    val room_version = "2.7.0"
  //  implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation(libs.androidx.navigation)
    implementation ("androidx.media3:media3-exoplayer:1.5.0")
    implementation ("androidx.media3:media3-ui:1.5.0")
    implementation ("androidx.media3:media3-common:1.5.0")
    implementation ("androidx.media3:media3-session:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation ("io.github.shashank02051997:FancyToast:2.0.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("com.airbnb.android:lottie-compose:6.3.0")
    implementation("androidx.compose.material:material-icons-extended:1.6.1")
    implementation("androidx.media3:media3-effect:1.7.1")


    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version") // Use kapt for Kotlin.
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.core:core-splashscreen:1.0.0")

    //Coil
    implementation("io.coil-kt.coil3:coil-compose:3.1.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.1.0")

    //Sql-ciper
    implementation( "net.zetetic:android-database-sqlcipher:4.5.3")
    implementation( "androidx.sqlite:sqlite:2.4.0")

    // For AppWidgets support
    implementation("androidx.glance:glance-appwidget:1.1.1")
   // For interop APIs with Material 3
    implementation("androidx.glance:glance-material3:1.1.1")
    // For interop APIs with Material 2
    implementation("androidx.glance:glance-material:1.1.1")

}