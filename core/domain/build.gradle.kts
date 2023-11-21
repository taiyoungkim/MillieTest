plugins {
    id("tydev.android.library")
    id("tydev.android.library.jacoco")
    kotlin("kapt")
}

android {
    namespace = "com.tydev.millietest.core.domain"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:network"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
