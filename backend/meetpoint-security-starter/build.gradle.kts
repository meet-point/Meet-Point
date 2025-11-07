plugins {
    `java-library`
    `maven-publish`
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter")

    implementation(platform("io.jsonwebtoken:jjwt-root:0.12.6"))
    implementation("io.jsonwebtoken:jjwt-api")

    runtimeOnly("io.jsonwebtoken:jjwt-impl")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.named("bootJar") {
    enabled = false
}

tasks.register("prepareKotlinBuildScriptModel") {
    doLast {
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            groupId = project.group.toString()
            artifactId = "meetpoint-security-spring-boot-starter"
            version = project.version.toString()
        }
    }

    repositories {
        mavenLocal()
    }
}