plugins {
    `java-library`
}

group = "net.coosanta.minecraftlaf"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    // none lmao!
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
