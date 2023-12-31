pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MillieTest"
include(":app")
include(":core:domain")
include(":core:model")
include(":core:network")
include(":core:data")
include(":core:local")
include(":feature:headline")
include(":core:ui")
include(":core:testing")
