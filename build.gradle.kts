plugins {
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    id("maven-publish")
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("plugin.jpa") version "1.6.10"
    kotlin("kapt") version "1.6.10"
}

allprojects {
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
        mavenLocal()
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
        api(group = "com.google.code.gson", name = "gson", version = "2.9.1")
        api(group = "org.apache.commons", name = "commons-lang3", version = "3.11")
        api(group = "com.google.guava", name = "guava", version = "31.1-jre")
    }
}

project(":forger-data-jpa") {
    dependencies {
        api(project(":forger-core"))
        api(group = "org.springframework.boot", name = "spring-boot-starter-data-jpa")
        api(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version = "1.6.4")
        implementation(group = "com.querydsl", name = "querydsl-jpa", version = "5.0.0")
        implementation(group = "com.querydsl", name = "querydsl-apt", version = "5.0.0")
    }
}

project(":forger-mp") {
    dependencies {
        api(project(":forger-core"))
        api(group = "com.baomidou", name = "mybatis-plus-boot-starter", version = "3.4.3.4")
    }
}

project(":forger-data-es") {
    dependencies {
        api(project(":forger-core"))
        api(group = "org.springframework.boot", name = "spring-boot-starter-data-elasticsearch")
        api(group = "commons-beanutils", name = "commons-beanutils", version = "1.9.4")
    }
}
project(":forger-security") {
    dependencies {
        api(project(":forger-core"))
        api(group = "org.springframework.boot", name = "spring-boot-starter-web")
        api(group = "org.springframework.boot", name = "spring-boot-starter-security")
        api(group = "cn.hutool", name = "hutool-all", version = "5.4.0")
    }
}

project(":forger-shiro") {
    dependencies {
        api(project(":forger-core"))
        implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
        api(group = "org.apache.shiro", name = "shiro-spring-boot-web-starter", version = "1.10.0")
    }
}
project(":forger-shiro-example") {
    dependencies {
        api(project(":forger-core"))
        api(project(":forger-shiro"))
        api(project(":forger-mp"))
        implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
        runtimeOnly(group = "com.h2database", name = "h2")
    }
}

project(":forger-workflow") {
    dependencies {
        api(project(":forger-core"))
        api(project(":forger-mp"))
        implementation(group = "org.springframework.boot", name = "spring-boot-starter-activemq")
        runtimeOnly(group = "com.h2database", name = "h2")
    }
}
