plugins {
    alias(libs.plugins.androidApplication)

    // Firebase plugins
    id("com.google.gms.google-services")
}

android {
    namespace = "edu.northeastern.firebasedemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "edu.northeastern.firebasedemo"
        minSdk = 34
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    /*
    Firebase dependencies:
    - firebase-bom: This is the Firebase Bill of Materials (BoM) that provides the recommended versions of the Firebase libraries to use.
    - firebase-analytics: This is the Firebase Analytics library. It is used to collect and analyze user data.
    - firebase-auth: This is the Firebase Authentication library. It is used to authenticate users and manage user accounts.
    - firebase-firestore: This is the Firebase Firestore library. It is used to store and sync data in real-time.
     */
    implementation(platform(libs.firebase.bom))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")

    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
}