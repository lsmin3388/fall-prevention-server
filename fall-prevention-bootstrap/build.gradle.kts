springBoot {
    mainClass.set("com.happyaging.fallprevention.FallPreventionApplication")
}

dependencies {
    implementation(project(":fall-prevention-common"))
    implementation(project(":fall-prevention-security"))
    implementation(project(":fall-prevention-core"))
    implementation(project(":fall-prevention-api"))
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    archiveFileName.set("app.jar")
    enabled = true
}
