

val ktorVersion = "2.3.12"





plugins {
  kotlin("jvm") version "2.0.10"
  kotlin("plugin.serialization") version "2.0.10"
  application
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("com.github.ajalt.clikt:clikt:4.4.0")
  implementation("com.github.ajalt.mordant:mordant-coroutines:2.7.2")

  // ktor client
  implementation("io.ktor:ktor-client-core:$ktorVersion")
  implementation("io.ktor:ktor-client-apache:$ktorVersion")
  implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
  implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
  implementation("ch.qos.logback:logback-classic:1.5.6")


  // serializer
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
  implementation("net.peanuuutz.tomlkt:tomlkt:0.4.0")

  // webp support...
  implementation("com.twelvemonkeys.imageio:imageio-core:3.11.0")
  implementation("com.twelvemonkeys.imageio:imageio-metadata:3.11.0")
  implementation("com.twelvemonkeys.imageio:imageio-webp:3.11.0")

  // other
  implementation("org.jline:jline:3.26.3")
}


java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

application {
  // Define the main class for the application.
  mainClass = "_4Tipsy.TinyCloudCLI.AppKt"
}





tasks.jar {
  manifest.attributes["Main-Class"] = "_4Tipsy.TinyCloudCLI.AppKt"
  val dependencies = configurations
    .runtimeClasspath
    .get()
    .map(::zipTree) // OR .map { zipTree(it) }
  from(dependencies)
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}