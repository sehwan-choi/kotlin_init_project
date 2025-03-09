plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "1.9.25"
	// 추가
	kotlin("plugin.allopen") version "2.0.21" // allOpen 에 지정한 어노테이션으로 만든 클래스에 open 키워드를 적용
	kotlin("plugin.noarg") version "2.0.21" // noArg 에 지정한 어노테이션으로 만든 클래스에 자동으로 no-arg 생성자를 생성

	// QueryDSL Kapt
	kotlin("kapt") version "2.1.10"

	kotlin("plugin.serialization") version "1.9.25"
}

group = "security.me.com"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2024.0.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("p6spy:p6spy:3.9.1")
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
	// querydsl
	implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
	implementation("com.querydsl:querydsl-apt:5.0.0:jakarta")
	implementation("jakarta.persistence:jakarta.persistence-api")
	implementation("jakarta.annotation:jakarta.annotation-api")

	kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
	kapt("org.springframework.boot:spring-boot-configuration-processor")

	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

	// JWT
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
	// Uncomment the next line if you want to use RSASSA-PSS (PS256, PS384, PS512) algorithms:
	//"org.bouncycastle:bcprov-jdk15on:1.70",
	 // or "io.jsonwebtoken:jjwt-gson:0.11.5" for gson
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

kapt {
	correctErrorTypes = true
	arguments {
		arg("querydsl.entityAccessors", "true") // Q타입에 getter/setter 메서드 생성
	}
}


kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}