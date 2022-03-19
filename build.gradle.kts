plugins {
    kotlin("jvm") version "1.6.10"
}

group = "org.example"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
}
// Use Library!!!
val shade = configurations.create("shade")
shade.extendsFrom(configurations.implementation.get())

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

tasks {

    javadoc {
        options.encoding = "UTF-8"
    }

    compileJava {
        options.encoding = "UTF-8"
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    
    processResources {
        filesMatching("*.yml") {
            expand(project.properties)
        }
    }
    
    create<Jar>("sourceJar") {
        archiveClassifier.set("source")
        from(sourceSets["main"].allSource)
    }

    jar {
        from (shade.map { if (it.isDirectory) it else zipTree(it) })
    }
}

