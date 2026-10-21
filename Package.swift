// swift-tools-version:5.9
import PackageDescription

// url ve checksum, tag atıldığında .github/workflows/release.yml tarafından otomatik güncellenir.
let package = Package(
    name: "SharedNetwork",
    platforms: [.iOS(.v15)],
    products: [
        .library(name: "SharedNetwork", targets: ["SharedNetwork"]),
    ],
    targets: [
        .binaryTarget(
            name: "SharedNetwork",
            url: "https://github.com/isacetin/shared_network_module/releases/download/v1.0.2/SharedNetwork.xcframework.zip",
            checksum: "a89003162e8cc70e15b503a7d7e0bd33e9f32a34a9b44bee645460f6a82b8499"
        ),
    ]
)
