plugins {
    id("org.springframework.boot") version "3.3.5"
}

springBoot {
    mainClass.set("com.happyaging.fallprevention.FallPreventionApplication")
}

dependencies {
    implementation(project(":fall-prevention-security"))
    implementation(project(":fall-prevention-core"))
    implementation(project(":fall-prevention-api"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    developmentOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
}

tasks{
    withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
        enabled = true
        archiveFileName.set("app.jar")
    }
}
