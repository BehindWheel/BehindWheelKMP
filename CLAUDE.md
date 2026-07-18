# Project Instructions

## Agent Behavior

- **Do not use Agent tool or any sub-agents.** Do all work directly in the main conversation thread,
  one step at a time.
- **Do not add `Co-Authored-By: Claude` to git commits.**

## Architecture Overview

**Compose Multiplatform** (iOS + Android) with multi-module architecture implementing **MVI**
pattern using:

- **Decompose** for navigation & lifecycle (not Jetpack Navigation)
- **MVIKotlin** for state management (not ViewModel)
- **Koin** for dependency injection (not Hilt/Dagger)

### Module Structure

```
kmp/
├── features/           # UI features using Decompose components
├── compose/           # Compose UI libraries (foundation, maps-compose, etc.)
├── libraries/         # Pure Kotlin utilities (no UI)
└── shared/            # Cross-cutting concerns (analytics, models, persistence)
```

**Convention plugins** in `build-logic/convention/`:

- `grodnoroads.multiplatform.library` — Standard KMP library setup
- `grodnoroads.kmp.compose` — Adds Compose + stability configuration
- `grodnoroads.application` — Android app configuration

## Patterns & Conventions

### Feature Module Pattern

Each feature follows this structure:

```
feature-name/
  src/commonMain/kotlin/com/egoriku/grodnoroads/[feature]/
    domain/
      component/       # Decompose components (not ViewModels)
      store/          # MVIKotlin stores (Intent, State, Label)
    screen/           # Compose UI
    di/              # Koin modules
```

### Decompose Components (not ViewModels)

Features use **Decompose components** for navigation and lifecycle:

```kotlin
// Builder function pattern
fun buildAlertsComponent(
  componentContext: ComponentContext
): AlertsComponent = AlertsComponentImpl(componentContext)

// Component implementation
internal class AlertsComponentImpl(
  componentContext: ComponentContext
) : AlertsComponent,
  ComponentContext by componentContext,
  KoinComponent {

  private val store = instanceKeeper.getStore<AlertsStore>(::get)
}
```

Navigation uses `childStack` (stack), `childSlot` (dialogs), with `Config` sealed classes for
routes.

### MVIKotlin Store Pattern

Stores replace ViewModels with **Intent → Executor → Reducer → State** flow:

```kotlin
interface MyStore : Store<Intent, State, Label> {
  sealed interface Intent { /* user actions */ }
  sealed interface Label { /* one-time events */ }
  data class State(/* UI state */)
}

// StoreFactory creates stores
internal class MyStoreFactory(
  private val storeFactory: StoreFactory,
  // ... dependencies
) {
  fun create(): MyStore = object : MyStore,
    Store<Intent, State, Label> by storeFactory.create(
      name = "MyStore",
      initialState = State(...
    ),
    executorFactory = coroutineExecutorFactory { ... },
  reducer = ReducerImpl
  )
  {}
}
```

Access stores via `instanceKeeper.getStore<StoreType>(::get)` in components.

### Dependency Injection

**Koin modules** are feature-scoped and aggregated in `kmp/features/root/di/KoinHelper.kt`:

```kotlin
fun appModule() = listOf(
  introModule,
  guidanceModule,
  alertsModule,
  // ... all feature modules
  appScopeModule,
  analyticsModule
)
```

Each feature has a `di/` package with a module:

```kotlin
val myFeatureModule = module {
  factory { MyStoreFactory(storeFactory = get(), ...).create() }
  singleOf(::MyRepositoryImpl) { bind<MyRepository>() }
}
```

### Platform-Specific Code

Use **expect/actual** declarations (not interfaces):

```kotlin
// commonMain
expect class AudioPlayer {
  fun play(uri: String)
}

// androidMain
actual class AudioPlayer(private val context: Context) { ... }

// iosMain
actual class AudioPlayer { ... }
```

**IMPORTANT**: If constants, functions, or code are used by **both iOS and Android** (not
platform-specific),
place them in **commonMain**, not duplicated in androidMain and iosMain. Only use platform-specific
sources
when the implementation differs between platforms.

```kotlin
// ✅ GOOD: Shared constant in commonMain
// commonMain/MapUpdater.kt
internal const val NAVIGATION_CAMERA_TILT = 55.0f

// ❌ BAD: Duplicated constants in platform sources
// androidMain/MapUpdaterAndroid.kt
private const val NAVIGATION_CAMERA_TILT = 55.0f
// iosMain/MapUpdaterIos.kt
private const val NAVIGATION_CAMERA_VIEWING_ANGLE = 55.0
```

### Build Files

Modules use convention plugins + type-safe project accessors:

```kotlin
plugins {
  alias(libs.plugins.grodnoroads.multiplatform.library)
  alias(libs.plugins.grodnoroads.kmp.compose) // if Compose UI
}

kotlin {
  configureTargets(namespace = "com.egoriku.grodnoroads.myfeature")

  sourceSets {
    commonMain.dependencies {
      implementation(projects.kmp.features.tabs) // type-safe accessor
      implementation(libs.decompose)
    }
  }
}
```

Version catalog: `gradle/libs.versions.toml`

## Compose

- Use `rememberMutableState { value }` instead of `val state by remember { mutableStateOf(value) }`
- When adding a new `@Composable` function, add a preview at the bottom of the file with
  `@PreviewGrodnoRoadsDarkLight` annotation and a wrapper `GrodnoRoadsM3ThemePreview` composable
- Do not pass `initial` to `collectAsState()` when collecting from a `StateFlow` — it already has a
  value
- Add an empty line after `var`/`val` declarations inside composable functions

## Build & Verify Commands

**To verify the project compiles:**

```bash
./gradlew app:android:assembleDebug
```

**To apply linting/formatting (Spotless):**

```bash
./gradlew spotlessApply
```

**Typical workflow:**

```bash
# 1. Make changes
# 2. Check compilation
./gradlew app:android:assembleDebug

# 3. Apply formatting
./gradlew spotlessApply

# 4. Run tests if needed
./gradlew test
```

**Run tests in a specific module:**

```bash
./gradlew :kmp:features:<module>:testAndroidHostTest
```

**Run a single test class:**

```bash
./gradlew :kmp:features:<module>:testAndroidHostTest --tests "com.egoriku.grodnoroads.<package>.<TestClass>"
```

**Compose metrics (performance analysis):**

```bash
./gradlew app:android:assembleRelease -PenableComposeCompilerReports=true
# Output: build/compose_metrics/
```

**BuildKonfig (version constants):**

```bash
./gradlew :kmp:shared:components:generateBuildKonfig
./gradlew :kmp:compose:maps-compose:generateBuildKonfig
```

## Code Style & Formatting

Enforced via Ktlint + Compose rules configured in root `build.gradle.kts`. Custom rules include:

- Material 3 only (Material 2 disallowed)
- Compose function naming
- Custom stability config in `config/compose-stability.config`

## iOS Development

Xcode project: `app/ios/grodno-roads-ios.xcodeproj`

## Anti-Patterns to Avoid

- ❌ Don't use Jetpack Navigation (use Decompose `childStack`)
- ❌ Don't create ViewModels (use Decompose Components + MVIKotlin Stores)
- ❌ Don't use Hilt/Dagger annotations (use Koin `module {}`)
- ❌ Don't reference Android SDK directly in commonMain (use expect/actual)
- ❌ Don't use Material 2 (Material 3 only, enforced by Spotless rules)
- ❌ Don't use `// region` / `// endregion` comments — extract functions instead

## Adding a New Feature

1. Create module structure: `kmp/features/my-feature/src/{commonMain,androidMain,iosMain}`
2. Add to `settings.gradle.kts`: `include(":kmp:features:my-feature")`
3. Create `build.gradle.kts` with convention plugins
4. Implement: `domain/component/`, `domain/store/`, `screen/`, `di/`
5. Add Koin module to `root/di/KoinHelper.kt`
6. Register in parent component's navigation (e.g., `MainFlowComponentImpl`)
7. Run `./gradlew spotlessApply`

## Branch Naming

Follow these patterns when creating branches:

| Type     | Pattern                 | Examples                                                    |
|----------|-------------------------|-------------------------------------------------------------|
| Feature  | `feature/<description>` | `feature/android-auto`, `feature/debug_menu`                |
| Bugfix   | `bug/<description>`     | `bug/map-rotation`, `bug/apache-crash-on-android10-devices` |
| Task     | `task/<description>`    | `task/prepare-release-1.7.0`, `task/correct-last-tag`       |
| Renovate | `renovate/<dependency>` | `renovate/firebase-firebase-ios-sdk-12.x`                   |
| Release  | `release`               | Main release branch (fixed name)                            |

**Guidelines:**

- Use lowercase with hyphens (`-`) for multi-word descriptions
- Keep descriptions short but descriptive
- Branch from `develop` (default branch)
- Delete branches after merging to `release`

## Prepare Android Release

When asked to prepare a release (e.g. "prepare release 1.6.0"), do the following steps in order:

### 1. Update version in `config/versioning/android.properties`

```properties
major=X
minor=Y
patch=Z
```

### 2. Update `CHANGELOG_ANDROID.md`

- Move all content from `## [Unreleased]` into a new versioned section `## [X.Y.Z] - YYYY-MM-DD` (
  use today's date)
- Leave `## [Unreleased]` empty at the top
- When adding entries to an existing `[Unreleased]` section, append new items at the **end** of the
  relevant category (e.g. `### Changed`), not at the top

Example structure:

```markdown
# Changelog (Android)

## [Unreleased]

## [1.6.0] - 2026-06-27

### Added

- ...

### Fixed

- ...

### Changed

- ...
```
