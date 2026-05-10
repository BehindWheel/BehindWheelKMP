// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_kmp_compose_maps_compose",
  platforms: [
    .iOS("16.0")
  ],
  products: [
    .library(
      name: "_kmp_compose_maps_compose",
      type: .none,
      targets: ["_kmp_compose_maps_compose"]
    )
  ],
  dependencies: [
    .package(
      url: "https://github.com/googlemaps/ios-maps-sdk.git",
      exact: "10.13.0"
    )
  ],
  targets: [
    .target(
      name: "_kmp_compose_maps_compose",
      dependencies: [
        .product(
          name: "GoogleMaps",
          package: "ios-maps-sdk"
        )
      ]
    )
  ]
)
