dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation ("org.springframework.boot:spring-boot-starter-validation")

    testImplementation ("org.testcontainers:postgresql")

    runtimeOnly ("org.postgresql:postgresql")
}

tasks.register("prepareKotlinBuildScriptModel") {
    doLast { }
}