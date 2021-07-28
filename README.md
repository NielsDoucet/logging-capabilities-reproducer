# logging-capabilities-reproducer

Run `./gradlew dependencyInsight --configuration runtimeClasspath --dependency org.slf4j:slf4j-simple` to see the correctly applied dependency substitution.

```
> Task :dependencyInsight
ch.qos.logback:logback-classic:1.2.4
   variant "runtime" [
      org.gradle.status              = release (not requested)
      org.gradle.usage               = java-runtime
      org.gradle.libraryelements     = jar
      org.gradle.category            = library

      Requested attributes not found in the selected variant:
         org.gradle.dependency.bundling = external
         org.gradle.jvm.version         = 8
   ]
   Selection reasons:
      - By constraint : dependency was locked to version '1.2.4'
      - By conflict resolution : On capability dev.jacomet.logging:slf4j-impl Logging capabilities plugin selected Slf4J binding

org.slf4j:slf4j-simple:1.7.30 -> ch.qos.logback:logback-classic:1.2.4
\--- org.owasp.antisamy:antisamy:1.6.3
     \--- runtimeClasspath

A web-based, searchable dependency report is available by adding the --scan option.

BUILD SUCCESSFUL in 540ms
1 actionable task: 1 executed
```

I could not get a full reproducer where the conflict resolution fails, but the output of such a case looks like this:
```
> Task :common-core:dependencyInsight
org.slf4j:slf4j-simple:1.7.32
   variant "runtime" [
      org.gradle.status              = release (not requested)
      org.gradle.usage               = java-runtime
      org.gradle.libraryelements     = jar
      org.gradle.category            = library

      Requested attributes not found in the selected variant:
         org.gradle.dependency.bundling = external
         org.gradle.jvm.version         = 8
   ]
   Selection reasons:
      - By constraint : dependency was locked to version '1.7.32'
      - By constraint : belongs to platform dev.jacomet.logging.align:slf4j:1.7.32
      - Forced
      - By ancestor

org.slf4j:slf4j-simple:{strictly 1.7.32} -> 1.7.32
\--- runtimeClasspath

org.slf4j:slf4j-simple:1.7.32
\--- org.springframework.boot:spring-boot-dependencies:2.5.3
     +--- runtimeClasspath (requested org.springframework.boot:spring-boot-dependencies:{strictly 2.5.3})
     \--- com.example:platform-bom:7.1.0
          \--- runtimeClasspath (requested com.example:platform-bom:7.+)

org.slf4j:slf4j-simple:1.7.30 -> 1.7.32
\--- org.owasp.antisamy:antisamy:1.6.3
     +--- runtimeClasspath (requested org.owasp.antisamy:antisamy:{strictly 1.6.3})
     \--- org.owasp.esapi:esapi:2.2.3.1
          +--- runtimeClasspath (requested org.owasp.esapi:esapi)
          \--- com.example:platform-bom:7.1.0
               \--- runtimeClasspath (requested com.example:platform-bom:7.+)

(*) - dependencies omitted (listed previously)

A web-based, searchable dependency report is available by adding the --scan option.

BUILD SUCCESSFUL in 1s
1 actionable task: 1 executed
```

A gradle scan dump from our internal gradle enterprise instance can be provided on demand: `s/z7fpqvunsauxs`.
