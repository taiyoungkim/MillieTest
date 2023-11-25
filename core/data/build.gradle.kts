plugins {
    id("tydev.android.library")
    id("tydev.android.library.jacoco")
    id("tydev.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.tydev.millietest.core.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:local"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
}
