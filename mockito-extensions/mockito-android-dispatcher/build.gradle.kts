plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "org.mockito.android.dispatcher"
    compileSdk = 33

    defaultConfig {
        applicationId = "org.mockito.android.dispatcher"
        minSdk = 28
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

// Expose the merged DEX output as a consumable configuration
val dispatcherDex: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage::class, "android-dex"))
    }
}

afterEvaluate {
    val mergeDex = tasks.named("mergeDexRelease")
    artifacts {
        add(dispatcherDex.name, mergeDex.map {
            (it as com.android.build.gradle.internal.tasks.DexMergingTask).outputDir.get()
        }) {
            builtBy(mergeDex)
        }
    }
}
