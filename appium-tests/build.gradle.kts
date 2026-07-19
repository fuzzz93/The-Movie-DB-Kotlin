import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.example.moviedb.e2e"
version = "1.0.0"

repositories {
    mavenCentral()
}

val aspectjVersion = "1.9.25.1"
val agent: Configuration by configurations.creating

dependencies {
    testImplementation("io.appium:java-client:10.1.1")

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
