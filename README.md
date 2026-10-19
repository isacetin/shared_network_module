# SharedNetwork

Android ve iOS uygulamalarının ortak kullandığı network katmanı. Backend cevabını tek yerde
(`sharedLogic/src/commonMain/kotlin/com/demo/sharednetwork/Mapper.kt`) dönüştürür.

## Yeni sürüm yayınlama

```bash
git tag v1.0.0
git push origin v1.0.0
```

- **iOS:** GitHub Actions (`.github/workflows/release.yml`) XCFramework'ü üretir, Release'e yükler
  ve tag'i güncel `Package.swift` (url + checksum) içeren commit'e taşır.
- **Android:** JitPack, tag ilk kez istendiğinde `jitpack.yml` ile projeyi derler.
  Durumu: https://jitpack.io/#isacetin/shared_network_module

## Android projesinde kullanım

`settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```

`app/build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.isacetin.shared_network_module:sharedLogic-android:v1.0.0")
}
```

`AndroidManifest.xml` içinde `<uses-permission android:name="android.permission.INTERNET" />` olmalı.

```kotlin
val network = SharedNetwork(baseUrl = "https://api.ornek.com")
val sonuc = network.kullaniciOzetiGetir(1) // coroutine içinde
```

## iOS projesinde kullanım

Xcode → File → Add Package Dependencies → `https://github.com/isacetin/shared_network_module`
→ sürüm seç → `SharedNetwork` ürününü ekle.

```swift
import SharedNetwork

let sonuc = try await SharedNetwork(baseUrl: "https://api.ornek.com").kullaniciOzetiGetir(userId: 1)
```

---

This is a Kotlin Multiplatform project targeting Android, iOS.

* [/iosApp](./iosApp/iosApp) contains an iOS application. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* [/sharedLogic](./sharedLogic/src) is for the code that will be shared between app targets in the project.
  The most important subfolder is [commonMain](./sharedLogic/src/commonMain/kotlin). If preferred, you
  can add code to the platform-specific folders here too.

* [/sharedUI](./sharedUI/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./sharedUI/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./sharedUI/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./sharedUI/src/jvmMain/kotlin)
    folder is the appropriate location.

### Running the apps

Use the run configurations provided by the run widget in your IDE's toolbar. You can also use these commands and options:

- Android app: `./gradlew :androidApp:assembleDebug`
- iOS app: open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…