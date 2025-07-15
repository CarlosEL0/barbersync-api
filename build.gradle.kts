plugins {
	java
	id("org.springframework.boot") version "3.3.1" // Usa tu versión, ej: "3.5.3"
	id("io.spring.dependency-management") version "1.1.5" // Usa tu versión, ej: "1.1.7"
}

tasks.withType<JavaCompile> {
	options.compilerArgs.add("-parameters")
}

group = "com.barbersync"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.mapstruct:mapstruct:1.5.5.Final") // Usa la versión que tenías si es diferente

	// --- DEPENDENCIAS JWT CORREGIDAS Y ACTUALIZADAS ---
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5") // Para la serialización JSON
	// --------------------------------------------------

	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.mysql:mysql-connector-j")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final") // Usa la versión que tenías
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}