dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation ("org.springframework.boot:spring-boot-starter-validation")
    //implementation("org.springframework.cloud:spring-cloud-starter-consul-discovery")
    //implementation("org.springframework.boot:spring-boot-starter-actuator")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation(platform("io.jsonwebtoken:jjwt-root:0.12.6"))
    implementation("io.jsonwebtoken:jjwt-api")

    //implementation("io.micrometer:micrometer-registry-prometheus")

    testImplementation ("org.testcontainers:postgresql")

    runtimeOnly("io.jsonwebtoken:jjwt-impl")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson")
    runtimeOnly ("org.postgresql:postgresql")
}

tasks.register("prepareKotlinBuildScriptModel") {
    doLast { }
}