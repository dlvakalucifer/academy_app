plugins {
    java
    id("org.springframework.boot") version "4.0.6"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "dev.wegner"
version = "0.0.1-SNAPSHOT"
description = "academy_app"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")

    //Cloud dependencies
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("io.minio:minio:8.5.17")

    //Security dependencies
    implementation("org.springframework.boot:spring-boot-starter-security")

    //Security dependencies: JWT
    implementation("io.jsonwebtoken:jjwt-api:0.12.7")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.7")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.7")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.assertj:assertj-core")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-actuator-test")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
    testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")

    //Security dependencies
    testImplementation("org.springframework.security:spring-security-test")

    //Docker Test Dependencies
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter:1.21.3")
    testImplementation("org.testcontainers:postgresql:1.21.3")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
