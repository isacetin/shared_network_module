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
            url: "https://github.com/isacetin/shared_network_module/releases/download/v1.0.0/SharedNetwork.xcframework.zip",
            checksum: "e82a9ef61894ac1ad5beb1622c579046cd3876b3660875ed201508489754702c"
        ),
    ]
)
