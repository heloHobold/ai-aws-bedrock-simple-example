plugins {
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
}

group = "com.github.heloHobold"
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
	implementation("org.springframework.boot:spring-boot")
	implementation("org.springframework.boot:spring-boot-autoconfigure")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("aws.sdk.kotlin:bedrock:1.2.54"){
		exclude("com.squareup.okhttp3:okhttp")
	}
	implementation("aws.sdk.kotlin:bedrockruntime:1.2.54"){
		exclude("com.squareup.okhttp3:okhttp")
	}
	implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.10")
	implementation("org.json:json:20231013")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
