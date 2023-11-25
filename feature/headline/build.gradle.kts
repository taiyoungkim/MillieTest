plugins {
    id("tydev.android.feature")
    id("tydev.android.library.compose")
    id("tydev.android.library.jacoco")
}

android {
    namespace = "com.tydev.millietest.feature.headline"
}

dependencies {
    implementation(project(":core:ui"))

    implementation(libs.androidx.appcompat)
}
