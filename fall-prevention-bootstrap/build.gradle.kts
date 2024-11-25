plugins {
    id("org.springframework.boot") version "3.3.5"
}

springBoot {
    mainClass.set("com.happyaging.fallprevention.FallPreventionApplication")
}

dependencies {
    implementation(project(":fall-prevention-common"))
    implementation(project(":fall-prevention-security"))
    implementation(project(":fall-prevention-core"))
    implementation(project(":fall-prevention-api"))

    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks{
    withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
        enabled = true
        archiveFileName.set("app.jar")
    }
}
