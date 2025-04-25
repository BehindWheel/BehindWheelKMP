<div align="center">
    <img alt="Icon" src="docs/logo.png" width="180" />
</div>

<h2 align="center">
    Behind the Wheel - actual information on the road
</h2>

<p align="center">
    <a target="_blank" href="https://github.com/BehindWheel/BehindWheelKMP/stargazers"><img src="https://img.shields.io/github/stars/egorikftp/GrodnoRoads.svg?style=for-the-badge"></a>
    <a target="_blank" href="https://github.com/BehindWheel/BehindWheelKMP/blob/release/LICENSE"><img src="https://img.shields.io/github/license/BehindWheel/BehindWheelKMP.svg?style=for-the-badge"></a>
    <a target="_blank" href="https://t.me/grodno_roads"><img src="https://img.shields.io/badge/Telegram%20Channel-blue?style=for-the-badge"></a>
</p>

| Android                                                                                                                     | iOS                                                                                        |
|-----------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------|
| [<img src="docs/google-play-badge.png" height="50">](https://play.google.com/store/apps/details?id=com.egoriku.grodnoroads) | [<img src="docs/appstore-badge.png" height="50">](https://apps.apple.com/app/id6737742923) |

## About

Compose Multiplatform application with iOS/Android target.

#### Key features:

- observe up-to-date information about traffic events throughout the Grodno region
- all information is automatically plotted on the map
- navigation mode allows to drive without being distracted by your phone
- voice alerts about upcoming events
- detailed information about the stationary and mobile speed cameras

The application can be customised for yourself:

- select your default city
- leave the types of events you need on the map
- the radius of voice alerts
- language preferences and e.t.

## Tech info

- Multi module architecture
- Built with Compose Multiplatform with iOS/Android targets
- Decompose for navigation and UI component lifecycle management
- MVIKotlin for unidirectional data flow architecture
- Koin for dependency injection
- DataStore for persistent preferences storage
- Firebase for analytics, crashlytics, and real-time database
- Custom Compose multiplatform Google Maps wrapper
- Voice alerts system using device TTS capabilities
- Material 3 design system
- Spotless for code style enforcement

## App screenshots

### Android

<img alt="Icon" src="docs/android/img_1.png" width="200" /><img alt="Icon" src="docs/android/img_2.png" width="200" /><img alt="Icon" src="docs/android/img_3.png" width="200" /><img alt="Icon" src="docs/android/img_4.png" width="200" /><img alt="Icon" src="docs/android/img_5.png" width="200" />

### iOS

<img alt="Icon" src="docs/ios/img_1.png" width="200" /><img alt="Icon" src="docs/ios/img_2.png" width="200" /><img alt="Icon" src="docs/ios/img_3.png" width="200" /><img alt="Icon" src="docs/ios/img_4.png" width="200" /><img alt="Icon" src="docs/ios/img_5.png" width="200" />

### Useful gradle commands

Update code style: `./gradlew spotlessApply`

Check code style: `./gradlew spotlessCheck`

Run compose metrics: `./gradlew app:android:assembleRelease -PenableComposeCompilerReports=true`

Generate kmp buildconfig: `./gradlew :kmp:shared:components:generateBuildKonfig`

Generate kmp buildconfig: `./gradlew :kmp:compose:maps-compose:generateBuildKonfig`

Build app debug: `./gradlew app:android:assembleDebug`

Build app release: `./gradlew app:android:assembleRelease`

Pods:
remove pod: `pod deintegrate`
init pod: `pod install`
