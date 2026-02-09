plugins {
    alias(libs.plugins.android.library)
    id("mockito.publication-conventions")
}

description = "Mockito for Android"

// Configuration to consume the dispatcher's DEX output
val dispatcherDex: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage::class, "android-dex"))
    }
}

val packageDispatcherJar by tasks.registering(Jar::class) {
    from(dispatcherDex)
    archiveFileName.set("dispatcher.jar")
    destinationDirectory.set(temporaryDir)
}

android {
    namespace = "org.mockito.android"
    compileSdk = 33

    defaultConfig {
        minSdk = 28

        ndk {
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    sourceSets {
        named("main") {
            resources.srcDirs(packageDispatcherJar.map { it.destinationDirectory })
        }
    }
}

tasks.preBuild {
    dependsOn(packageDispatcherJar)
}

dependencies {
    api(project(":mockito-core"))
    implementation(libs.dexmaker)
    dispatcherDex(project(":mockito-extensions:mockito-android-dispatcher"))
}
