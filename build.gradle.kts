import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.4.30"
	id("org.jetbrains.compose") version "0.3.1"
	id("org.springframework.boot") version "2.4.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "se.newton"
version = "1.0"

repositories {
	jcenter()
	mavenCentral()
	maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

dependencies {
	val retrofitVersion = "2.9.0"
	val okhttpVersion = "4.9.1"
	implementation(compose.desktop.currentOs)
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	// Kotlin
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.3")

	// Retrofit
	implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
	implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")
	implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion") {
		// exclude Retrofit’s OkHttp peer-dependency module and define our own module import
		exclude(module = "okhttp")
	}
	implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
	implementation("com.jakewharton.retrofit:retrofit2-reactor-adapter:2.1.0")
	implementation("com.squareup.retrofit2:adapter-rxjava:2.9.0")

	implementation("io.projectreactor.netty:reactor-netty-core:1.0.6")
	implementation("io.projectreactor.netty:reactor-netty-http:1.0.6")
}

tasks.withType<KotlinCompile>() {
	kotlinOptions.jvmTarget = "11"
}

compose.desktop {
	application {
		mainClass = "MainKt"

		nativeDistributions {
			targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
			packageName = "Newton AlgoCompose"
			packageVersion = "1.0.0"
		}
	}
}