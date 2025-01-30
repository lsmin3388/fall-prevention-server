plugins {
	id("java")
	id("io.spring.dependency-management") version "1.1.6"
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

allprojects {
	group = "com.happyaging.fallprevention"
	version = "0.1-SNAPSHOT"

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

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "java")
	apply(plugin = "java-library")
	apply(plugin = "io.spring.dependency-management")

	java {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}

	dependencyManagement {
		imports {
			mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.5")
		}
	}

	dependencies {
		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")
		implementation("org.springframework.boot:spring-boot-starter-validation")
	}
}
