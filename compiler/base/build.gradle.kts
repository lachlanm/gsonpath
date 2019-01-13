import org.gradle.internal.jvm.Jvm

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.7.0")
    }
}

plugins {
    java
    kotlin("jvm") version ProjectProperties.Versions.KOTLIN_VERSION
    id("com.vanniktech.maven.publish") version "0.7.0"
}

java {
    targetCompatibility = JavaVersion.VERSION_1_7
    sourceCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    implementation(ProjectProperties.Dependencies.KOTLIN_STD_LIB)
    implementation(ProjectProperties.Dependencies.JAVAPOET)
    implementation(files(Jvm.current().getToolsJar()))

    testImplementation(ProjectProperties.Dependencies.JUNIT)
}
