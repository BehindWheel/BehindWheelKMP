// swift-tools-version: 5.9

import PackageDescription

let package = Package(
    name: "Firebase", platforms: [.iOS(.v15)],
    products: [
        .library(name: "FirebaseBinaries", targets: ["FirebaseBinariesTarget"])
    ],
    dependencies: [
        .package(url: "https://github.com/google/promises.git", "2.3.1"..<"3.0.0"),
    ],
    targets: [
        .binaryTarget(name: "FirebaseAnalytics", path: "Frameworks/FirebaseAnalytics/FirebaseAnalytics.xcframework"),
        .binaryTarget(name: "FirebaseCore", path: "Frameworks/FirebaseAnalytics/FirebaseCore.xcframework"),
        .binaryTarget(name: "FirebaseCoreInternal", path: "Frameworks/FirebaseAnalytics/FirebaseCoreInternal.xcframework"),
        .binaryTarget(name: "FirebaseInstallations", path: "Frameworks/FirebaseAnalytics/FirebaseInstallations.xcframework"),
        .binaryTarget(name: "GoogleAppMeasurement", path: "Frameworks/FirebaseAnalytics/GoogleAppMeasurement.xcframework"),
        .binaryTarget(name: "GoogleUtilities", path: "Frameworks/FirebaseAnalytics/GoogleUtilities.xcframework"),
        .binaryTarget(name: "nanopb", path: "Frameworks/FirebaseAnalytics/nanopb.xcframework"),
        
        .binaryTarget(name: "FirebaseCrashlytics", path: "Frameworks/FirebaseCrashlytics/FirebaseCrashlytics.xcframework"),
        .binaryTarget(name: "FirebaseSessions", path: "Frameworks/FirebaseCrashlytics/FirebaseSessions.xcframework"),
        .binaryTarget(name: "GoogleDataTransport", path: "Frameworks/FirebaseCrashlytics/GoogleDataTransport.xcframework"),
        
        .binaryTarget(name: "abseil", path: "Frameworks/FirebaseFirestore/abseil.xcframework"),
        .binaryTarget(name: "BoringSSL-GRPC", path: "Frameworks/FirebaseFirestore/BoringSSL-GRPC.xcframework"),
        .binaryTarget(name: "FirebaseAppCheckInterop", path: "Frameworks/FirebaseFirestore/FirebaseAppCheckInterop.xcframework"),
        .binaryTarget(name: "FirebaseCoreExtension", path: "Frameworks/FirebaseFirestore/FirebaseCoreExtension.xcframework"),
        
        .binaryTarget(name: "FirebaseFirestore", path: "Frameworks/FirebaseFirestore/FirebaseFirestore.xcframework"),
        .binaryTarget(name: "FirebaseFirestoreInternal", path: "Frameworks/FirebaseFirestore/FirebaseFirestoreInternal.xcframework"),
        .binaryTarget(name: "FirebaseSharedSwift", path: "Frameworks/FirebaseFirestore/FirebaseSharedSwift.xcframework"),
        .binaryTarget(name: "gRPC-C++", path: "Frameworks/FirebaseFirestore/gRPC-C++.xcframework"),
        .binaryTarget(name: "gRPC-Core", path: "Frameworks/FirebaseFirestore/gRPC-Core.xcframework"),
        .binaryTarget(name: "leveldb-library", path: "Frameworks/FirebaseFirestore/leveldb-library.xcframework"),
        
        .target(
            name: "FirebaseBinariesTarget",
            dependencies: [
                .product(name: "FBLPromises", package: "Promises"),
                .product(name: "Promises", package: "Promises"),
                
                .target(name: "FirebaseAnalytics"),
                .target(name: "FirebaseCore"),
                .target(name: "FirebaseCoreInternal"),
                .target(name: "FirebaseInstallations"),
                .target(name: "GoogleAppMeasurement"),
                .target(name: "GoogleDataTransport"),
                .target(name: "GoogleUtilities"),
                .target(name: "nanopb"),
                .target(name: "FirebaseCrashlytics"),
                .target(name: "FirebaseSessions"),
                .target(name: "abseil"),
                .target(name: "BoringSSL-GRPC"),
                .target(name: "FirebaseAppCheckInterop"),
                .target(name: "FirebaseCoreExtension"),
                .target(name: "FirebaseFirestore"),
                .target(name: "FirebaseFirestoreInternal"),
                .target(name: "FirebaseSharedSwift"),
                .target(name: "gRPC-C++"),
                .target(name: "gRPC-Core"),
                .target(name: "leveldb-library"),
            ]
        )
    ]
)
