
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.3.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
}

kotlin {
    jvmToolchain(11)
}

group = "ru.weather.asyncan"
version = "0.0.1"

application {
    mainClass.set("ru.weather.asyncan.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

ktor {
    fatJar {
        archiveFileName.set("fat.jar")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")

    //Serialization
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    //KMongo
    implementation("org.litote.kmongo:kmongo-coroutine:4.9.0")
    implementation("org.litote.kmongo:kmongo-serialization:4.9.0")

    //Json
    implementation ("com.google.code.gson:gson:2.10.1")

    implementation("io.github.smiley4:ktor-swagger-ui:2.9.0")
}
