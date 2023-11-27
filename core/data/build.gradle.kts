plugins {
    id("tydev.android.library")
    id("tydev.android.library.jacoco")
    id("tydev.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.tydev.millietest.core.data"
}

dependencies {
    implementation(project(":core:local"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(project(":core:local"))
}
