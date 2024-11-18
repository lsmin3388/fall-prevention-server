plugins {
	id("java")
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.happyaging.fallprevention"
version = "0.1-SNAPSHOT"

repositories {
	mavenCentral()
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

subprojects {
	group = rootProject.group
	version = rootProject.version

	apply(plugin = "java")
	apply(plugin = "io.spring.dependency-management")

	java {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}

	dependencyManagement {
		imports {
			mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.1")
		}
	}

	dependencies {
		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")
		implementation("org.springframework.boot:spring-boot-starter-validation")
	}

	repositories {
		mavenCentral()
	}

	tasks {
		withType<JavaCompile> {
			options.encoding = "UTF-8"
		}
		withType<Test> {
			useJUnitPlatform()
		}
		withType<Jar> {
			enabled = true
		}
	}
}
