# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

GrodnoRoads — Compose Multiplatform app (Android/iOS) for real-time traffic events in the Grodno region. Features include automatic map plotting, navigation mode with voice alerts, speed camera details, and customizable settings.

## Build Commands

```bash
# Format code (run before committing)
./gradlew spotlessApply

# Check formatting
./gradlew spotlessCheck

# Build
./gradlew app:android:assembleDebug
./gradlew app:android:assembleRelease

# Run tests
./gradlew testDebugUnitTest

# Generate BuildKonfig (after changing build config values)
./gradlew :kmp:shared:components:generateBuildKonfig
./gradlew :kmp:compose:maps-compose:generateBuildKonfig

# Compose compiler metrics
./gradlew app:android:assembleRelease -PenableComposeCompilerReports=true
```

CI pipeline runs: `spotlessCheck` → `testDebugUnitTest` → `app:android:assembleDebug`

## Architecture

### Navigation — Decompose + MVIKotlin

The app uses **Decompose** for tree-based navigation and **MVIKotlin** for unidirectional state management. Each feature has this structure:

- **Component** (interface + impl) — Decompose component, the feature's entry point. Created via top-level `buildXxxComponent()` factory functions.
- **Store** (interface + factory) — MVIKotlin store with `Intent → Bootstrapper → Action → Reducer → State` flow. Stores are obtained via `instanceKeeper.getStore()` and bound to Decompose lifecycle.
- **UI** — Compose function rendering `Store.states` and dispatching `Store.accept(intent)`.

Root navigation: `RootComponent` uses `childStack` with `Intro` → `MainFlow` configs. Feature components are built in factory functions and receive `ComponentContext` + callback lambdas.

### Dependency Injection — Koin

All DI is in `:kmp:features:root`. `KoinHelper.kt` defines `appModule()` which aggregates every feature's Koin module. Platform-specific modules use `expect/actual` (`platformDataStoreModule`). Singletons: `StoreFactory`, Firebase services (analytics, crashlytics, database, firestore).

Components access dependencies via `KoinComponent` interface (`inject`, `get`). Koin is initialized in platform-specific `KoinInit` (Android: `RoadsApplication.onCreate()`, iOS: `KoinInit.ios.kt`).

### Module Structure

- `app/android` — Android shell (Application, MainActivity, signing, Firebase init)
- `kmp/features/*` — Feature modules (root, intro, mainflow, guidance, eventReporting, specialEventReminder, quickSettings, appSettings, tabs)
- `kmp/features/settings/*` — Settings submodules (alerts, appearance, debugTools, changelog, faq, map)
- `kmp/compose/*` — Shared Compose UI kit (common-ui, foundation/{core,preview,theme,uikit,icons}, location-requester, maps-compose, resources, snackbar)
- `kmp/shared/*` — Shared logic (analytics, audioplayer, components, crashlytics, formatter, geolocation, models, persistent)
- `kmp/libraries/*` — Utility libraries (datastore, extensions, location, logger, suntime)
- `build-logic/convention` — Gradle convention plugins: `grodnoroads.application`, `grodnoroads.kmp.compose`, `grodnoroads.multiplatform-library`

### Source Set Layout (KMP modules)

```
commonMain/     — shared Kotlin code
androidMain/    — Android-specific implementations
iosMain/        — iOS-specific implementations
commonTest/     — shared tests
```

## Key Technical Details

- **SDK**: compileSdk/targetSdk 36, minSdk 23, iOS minTarget 16.0
- **JDK**: 17 (Temurin)
- **Kotlin**: 2.4.0 with Compose compiler plugin
- **Compose Multiplatform**: 1.11.1 (Material 3, disallow Material 2)
- **Firebase**: BOM 34.14.1, GitLive Firebase SDK 2.4.0 for KMP
- **Signing**: Debug uses local keystore; Release reads from environment variables
- **Versioning**: `config/versioning/android.properties` (BUILD_VERSION, SUB_VERSION, VERSION)
- **Locales**: `en`, `ru`, `be`
- **Secrets**: `secrets.properties` + `google-services.json` (Android) / `GoogleService-Info.plist` (iOS) — not in repo, injected by CI

## Code Style

Spotless with Ktlint 1.8.0 + Compose rules (0.5.9). Key rules:
- Material 2 is disallowed (`compose_disallow_material2 = true`)
- Preview naming enabled with suffix strategy
- Composable function naming ignores ktlint naming convention
- `@Composable`-annotated functions exempt from function naming rules
- CompositionLocal allowlist check disabled

## Git Conventions

- Do NOT add `Co-Authored-By` to commit messages
