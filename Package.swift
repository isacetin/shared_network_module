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
            url: "https://github.com/isacetin/shared_network_module/releases/download/v1.0.1/SharedNetwork.xcframework.zip",
            checksum: "f1fff26248d65f4658e9b0abdcab4a020d6378569d9edb9b058e301f6045f368"
        ),
    ]
)
