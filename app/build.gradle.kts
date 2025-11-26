plugins {
    id("com.android.application")
    // Pastikan plugin Google Services sudah ada di sini
    id("com.google.gms.google-services")
}

android {
    namespace = "com.f52123078.aplikasibelajarmandiri" // Ganti dengan package name Anda
    compileSdk = 34 // Anda bisa sesuaikan

    defaultConfig {
        applicationId = "com.f52123078.aplikasibelajarmandiri"
        minSdk = 24 // Sesuai permintaan Anda
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    // Menggunakan Java, bukan Kotlin (jika Anda tidak mencampur)
    // kotlinOptions {
    //     jvmTarget = "1.8"
    // }

    // Aktifkan ViewBinding (Penting!)
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Firebase (Pastikan Anda sudah punya ini)
    implementation(platform("com.google.firebase:firebase-bom:34.4.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")

    // UI Components
    implementation("androidx.core:core-ktx:1.13.1") // (atau non-ktx jika murni Java)
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // (Opsional tapi direkomendasikan untuk UI yang bagus)
    implementation("com.airbnb.android:lottie:6.4.1")
    implementation(libs.firebase.firestore)
    implementation(libs.navigation.fragment) // Untuk animasi loading
}
