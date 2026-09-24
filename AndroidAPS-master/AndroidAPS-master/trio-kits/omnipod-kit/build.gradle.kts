plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "app.trio.kits.omnipod"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    // Kable for modern BLE (Omnipod DASH connects directly via BLE)
    implementation("com.juul.kable:core:0.29.0")
    
    // Core references
    implementation(project(":core:interfaces"))
}
