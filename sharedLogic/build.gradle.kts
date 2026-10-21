import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.kotlinSerialization)
    `maven-publish`
}

// JitPack çok modüllü repolarda "com.github.<kullanıcı>.<repo>" grubunu ve tag'i sürüm olarak kullanır.
group = System.getenv("GROUP")?.let { "$it.${System.getenv("ARTIFACT")}" }
    ?: "com.github.isacetin.shared_network_module"
version = System.getenv("VERSION") ?: "1.0.0-SNAPSHOT"

kotlin {
    // Kütüphaneyi kullanan projeler Kotlin 2.2 ile (kapt/Dagger gibi metadata okuyan araçlar dahil)
    // sorunsuz derlensin diye 2.2 seviyesinde derliyoruz ve stdlib 2.2 istiyoruz.
    coreLibrariesVersion = "2.2.21"
    compilerOptions {
        languageVersion = KotlinVersion.KOTLIN_2_2
        apiVersion = KotlinVersion.KOTLIN_2_2
    }

    val xcf = XCFramework("SharedNetwork")
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "SharedNetwork"
            isStatic = true
            xcf.add(this)
        }
    }
    
    android {
       namespace = "com.isacetin.shared_network_module.sharedLogic"
       compileSdk = libs.versions.sharedLogic.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()
    
       compilerOptions {
           jvmTarget = JvmTarget.JVM_11
       }
       androidResources {
           enable = true
       }
       withHostTest {
           isIncludeAndroidResources = true
       }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.serialization.kotlinxJson)
            implementation(libs.kotlinx.coroutines.core)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}