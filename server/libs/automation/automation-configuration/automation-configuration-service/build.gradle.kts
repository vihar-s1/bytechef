dependencies {
    implementation("org.apache.commons:commons-lang3")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.data:spring-data-jdbc")
    implementation(project(":server:libs:atlas:atlas-configuration:atlas-configuration-repository:atlas-configuration-repository-api"))
    implementation(project(":server:libs:atlas:atlas-coordinator:atlas-coordinator-api"))
    implementation(project(":server:libs:core:commons:commons-data"))
    implementation(project(":server:libs:core:commons:commons-util"))
    implementation(project(":server:libs:automation:automation-configuration:automation-configuration-api"))
    implementation(project(":server:libs:platform:platform-connection:platform-connection-api"))
    implementation(project(":server:libs:platform:platform-security:platform-security-api"))
    implementation(project(":server:libs:platform:platform-user:platform-user-api"))
    implementation(project(":server:libs:platform:platform-workflow:platform-workflow-execution:platform-workflow-execution-api"))

    testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    testImplementation(project(":server:libs:atlas:atlas-configuration:atlas-configuration-repository:atlas-configuration-repository-jdbc"))
    testImplementation(project(":server:libs:atlas:atlas-configuration:atlas-configuration-service"))
    testImplementation(project(":server:libs:config:liquibase-config"))
    testImplementation(project(":server:libs:platform:platform-category:platform-category-service"))
    testImplementation(project(":server:libs:platform:platform-component:platform-component-registry:platform-component-registry-service"))
    testImplementation(project(":server:libs:platform:platform-configuration:platform-configuration-service"))
    testImplementation(project(":server:libs:platform:platform-connection:platform-connection-api"))
    testImplementation(project(":server:libs:platform:platform-tag:platform-tag-service"))
    testImplementation(project(":server:libs:test:test-int-support"))
}
