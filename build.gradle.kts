
plugins {
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    id("maven-publish")
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.spring") version "1.7.20"
    kotlin("plugin.jpa") version "1.7.20"
    kotlin("kapt") version "1.7.20"
}

allprojects{
    group = "com.github.wenslo"
    version = "0.0.1-SNAPSHOT"

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
    repositories {
        mavenCentral()
    }
}