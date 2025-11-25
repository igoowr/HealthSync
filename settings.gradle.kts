pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

// O bloco plugins aqui é o que define as versões para TODO o projeto
plugins {
    id("com.android.application") version "8.12.3" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false // Garanta que esta versão é a 2.0.21
    id("com.google.devtools.ksp") version "2.0.21-1.0.25" apply false
}

rootProject.name = "HealthSync"
include(":app")