plugins {
    id("tydev.android.library")
    id("tydev.android.library.jacoco")
    id("tydev.android.hilt")
    id("tydev.android.room")
}

android {
    namespace = "com.tydev.millietest.core.local"
    defaultConfig {
        testInstrumentationRunner =
            "com.tydev.core.millietest.testing.MyTestRunner"
    }
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
}
