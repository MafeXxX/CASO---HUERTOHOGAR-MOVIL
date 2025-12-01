plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
}

android {
    // ✔ Debe coincidir con tus paquetes Kotlin
    namespace = "com.example.proyectologin005d"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.proyectologin005d"
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
    kotlinOptions { jvmTarget = "11" }

    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = "1.5.15" }
}

dependencies {


    // ===== Base desde Version Catalog =====
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // ===== Navegación Compose =====
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // ===== Íconos =====
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")

    // ===== Imágenes (Coil) =====
    implementation("io.coil-kt:coil-compose:2.7.0")

    // ===== MVVM + DataStore (sesión) =====
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // ===== Room (BD local) =====
    implementation("androidx.room:room-runtime:2.6.1")
    implementation(libs.material3)
    implementation(libs.ui.test.junit4)
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // ===== Mapbox (reemplazo de Google Maps) =====
    implementation("com.mapbox.maps:android-ndk27:11.16.4")
    implementation("com.mapbox.extension:maps-compose-ndk27:11.16.4")

    //API imprementacion
    // Retrofit y Gson Converter
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    // Corrutinas para trabajo asincrónico
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
    // Jetpack Compose
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")


    // testing implementation
    // TEST DEPENDENCIES (CONFIGURACIÓN CORRECTA Y LIMPIA)
    // Kotest (solo estas 2 son necesarias)
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
// MockK
    testImplementation("io.mockk:mockk:1.13.10")
// Coroutines Test

// AndroidX Test
    testImplementation("androidx.arch.core:core-testing:2.2.0")
// JUnit 5 (solo engine, Kotest usa JUnit 5)
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")



    // ===== Tests =====
    testImplementation(libs.junit)
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

tasks.withType<Test> {
    useJUnitPlatform() // <<< NECESARIO
    testLogging {
        events("passed", "failed", "skipped")
    }
}
