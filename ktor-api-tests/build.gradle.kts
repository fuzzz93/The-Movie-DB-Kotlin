import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.3.21"
    kotlin("plugin.serialization") version "2.3.21"
}

group = "com.example.moviedb.api"
version = "1.0.0"

repositories {
    mavenCentral()
}

val ktorVersion = "3.5.1"
val aspectjVersion = "1.9.25.1"
val agent: Configuration by configurations.creating

dependencies {
    testImplementation("io.ktor:ktor-client-core:$ktorVersion")
    testImplementation("io.ktor:ktor-client-cio:$ktorVersion")
    testImplementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    testImplementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    testImplementation(platform("org.junit:junit-bom:5.14.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation(platform("io.qameta.allure:allure-bom:2.35.3"))
    testImplementation("io.qameta.allure:allure-junit5")

    agent("org.aspectj:aspectjweaver:$aspectjVersion")
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("-javaagent:${agent.singleFile}")
    systemProperty(
        "allure.results.directory",
        layout.buildDirectory.dir("allure-results").get().asFile.absolutePath
    )
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}
