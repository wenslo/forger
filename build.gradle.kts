
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

subprojects {
    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("maven-publish")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.plugin.jpa")
        plugin("org.jetbrains.kotlin.kapt")
    }
    dependencies {
    }
    val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
    compileKotlin.kotlinOptions {
        jvmTarget = "17"
    }
    val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
    compileTestKotlin.kotlinOptions {
        jvmTarget = "17"
    }


    tasks {
        jar {
            enabled = true
        }
        bootJar {
            enabled = false
        }
    }
}

project(":forger-core") {
    dependencies {
        compileOnly(group = "org.springframework.boot", name = "spring-boot-starter-data-jpa")
    }
}

