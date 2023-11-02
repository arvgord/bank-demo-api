import com.google.protobuf.gradle.id

plugins {
    kotlin("jvm")
    id("com.google.protobuf")
    id("maven-publish")
}

group = "com.arvgord"
version = "0.0.2"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.grpc:grpc-kotlin-stub:${property("grpcKotlinVersion")}")
    implementation("io.grpc:grpc-protobuf:${property("grpcProtobufVersion")}")
    implementation("com.google.protobuf:protobuf-kotlin:${property("protobufKotlinVersion")}")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${property("protobufKotlinVersion")}"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${property("grpcProtobufVersion")}"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${property("grpcKotlinVersion")}:jdk8@jar"
        }
        id("protoc-gen-js") {
            path = projectDir.path.plus("/tools/protoc-gen-js-3.21.2-linux-x86_64")
        }
        id("protoc-gen-grpc-web") {
            path = projectDir.path.plus("/tools/protoc-gen-grpc-web-1.4.2-linux-x86_64")
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
                id("protoc-gen-js") {
                    option("import_style=commonjs,binary")
                }
                id("protoc-gen-grpc-web") {
                    option("import_style=commonjs+dts,mode=grpcweb")
                }
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
}

sourceSets {
    main {
        proto {
            srcDirs(projectDir)
            exclude("build/**")
            exclude("gradle")
            exclude(".gradle")
            exclude(".idea")
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/arvgord/bank-demo-api")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
        }
    }
}

tasks.register<Copy>("buildAndCopy") {
    from(
        projectDir.path.plus("/build/generated/source/proto/main/protoc-gen-js"),
        projectDir.path.plus("/build/generated/source/proto/main/protoc-gen-grpc-web")
    )
    into(projectDir.path.plus("/npm_package/"))
}
tasks.register<Delete>("cleanNpmDir") {
    delete(projectDir.path.plus("/npm_package/bankdemo/"))
    delete(projectDir.path.plus("/npm_package/node_modules/"))
    delete(projectDir.path.plus("/npm_package/package-lock.json"))
}
tasks["buildAndCopy"].dependsOn("build")
tasks["clean"].dependsOn("cleanNpmDir")
tasks["processResources"].dependsOn("generateProto")
tasks["extractTestProto"].dependsOn("generateProto")