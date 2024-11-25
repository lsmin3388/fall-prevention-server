dependencies {
    implementation(project(":fall-prevention-common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("com.h2database:h2")
    implementation("org.postgresql:postgresql")
}
