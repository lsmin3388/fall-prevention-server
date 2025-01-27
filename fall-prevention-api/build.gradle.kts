dependencies {
    implementation(project(":fall-prevention-core"))
    implementation(project(":fall-prevention-common"))
    implementation(project(":fall-prevention-security"))
    implementation(project(":fall-prevention-analysis"))

    // API 문서화
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
}
