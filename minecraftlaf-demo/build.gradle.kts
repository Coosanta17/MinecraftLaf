plugins {
    application
    java
}

group = "net.coosanta.minecraftlaf-demo"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":minecraftlaf"))
}

application {
    mainClass.set("net.coosanta.minecraftlaf.demo.Main")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}