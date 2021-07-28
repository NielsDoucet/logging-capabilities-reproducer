plugins {
    java
    id("dev.jacomet.logging-capabilities") version "0.9.0"
}

repositories {
    mavenCentral()
}

configurations.all {
    resolutionStrategy {
        activateDependencyLocking()
    }
}

dependencies {
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("org.owasp.antisamy:antisamy:1.6.3")

    runtimeOnly("ch.qos.logback:logback-classic:1.2.4")
}

loggingCapabilities {
    enforceLogback()
}

val resolveAndLockAll by tasks.registering {
    doFirst {
        assert(gradle.startParameter.isWriteDependencyLocks)
    }
    doLast {
        configurations.filter { it.isCanBeResolved }.forEach { it.resolve() }
    }
}
