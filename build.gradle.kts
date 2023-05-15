plugins {
    java
    id("org.springframework.boot") version "2.7.11"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.projectlombok:lombok:1.18.26")
    runtimeOnly ("com.mysql:mysql-connector-j")
    implementation("javax.xml.bind:jaxb-api:2.1")
    implementation ("org.springframework.boot:spring-boot-starter-jdbc")
    implementation ("org.apache.directory.studio:org.apache.commons.io:2.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
