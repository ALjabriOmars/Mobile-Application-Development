pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS) // Prefer repositories defined here
    repositories {
        google() // Google's Maven repository for Android dependencies
        mavenCentral() // Central repository for Java and Kotlin libraries
    }
}

rootProject.name = "MyTravelApp"
include(":app")
