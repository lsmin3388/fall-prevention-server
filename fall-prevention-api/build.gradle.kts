dependencies {
    implementation(project(":fall-prevention-core"))
    implementation(project(":fall-prevention-common"))
    implementation(project(":fall-prevention-security"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
}
