dependencies {
    // Spring Boot Starters
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-security")

    // JWT Dependencies
    api("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    // Google API Libraries for YouTube Data API
    api("com.google.api-client:google-api-client:1.33.2")
    api("com.google.apis:google-api-services-youtube:v3-rev222-1.25.0")
    api("com.google.guava:guava:32.1.2-jre")

    // WebFlux
    api("org.springframework.boot:spring-boot-starter-webflux")

    // Other Common Libraries
    implementation("org.apache.tika:tika-core:2.9.2")
}
