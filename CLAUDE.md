# Project Instructions

## Prepare Android Release

When asked to prepare a release (e.g. "prepare release 1.6.0"), do the following steps in order:

### 1. Update version in `config/versioning/android.properties`

```properties
major=X
minor=Y
patch=Z
```

### 2. Update `CHANGELOG_ANDROID.md`

- Move all content from `## [Unreleased]` into a new versioned section `## [X.Y.Z] - YYYY-MM-DD` (use today's date)
- Leave `## [Unreleased]` empty at the top
- When adding entries to an existing `[Unreleased]` section, append new items at the **end** of the relevant category (e.g. `### Changed`), not at the top

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

## Compose

- Use `rememberMutableState { value }` instead of `val state by remember { mutableStateOf(value) }`
- When adding a new `@Composable` function, add a preview at the bottom of the file with `@PreviewGrodnoRoadsDarkLight` annotation and a wrapper `GrodnoRoadsM3ThemePreview` composable
- Do not pass `initial` to `collectAsState()` when collecting from a `StateFlow` — it already has a value
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
