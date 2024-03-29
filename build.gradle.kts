// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        // Make sure that you have the following two repositories
        google()  // Google's Maven repository

        mavenCentral()  // Maven Central repository

    }
    dependencies {

        val nav_version = "2.5.3"
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")

    }
}
plugins {
    id("com.android.application") version "8.1.1" apply false
    kotlin("jvm") version "1.9.21"
    id ("com.android.library") version "8.0.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id ("com.google.dagger.hilt.android") version "2.44" apply false

}