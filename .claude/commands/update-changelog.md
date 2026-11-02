Update the changelog based on uncommitted changes.

## Steps

1. Get uncommitted changes:
   ```bash
   git diff --staged
   git diff
   git ls-files --others --exclude-standard
   ```
   For each untracked file, read its content to classify it (e.g. `cat <file>`).

2. Analyze the changes and determine:
   - **Platform**: Android only, iOS only, or Both
   - **Category**: Added, Fixed, or Changed

3. For each meaningful change, add a bullet point to the appropriate changelog file(s):
   - `CHANGELOG_ANDROID.md` for Android changes
   - `CHANGELOG_IOS.md` for iOS changes
   - Both files for shared changes

4. Add entries under `## [Unreleased]` in the correct category:
   - `### Added` — new features or capabilities
   - `### Fixed` — bug fixes
   - `### Changed` — improvements, updates, refactors
   - Create the category heading if it does not already exist under `## [Unreleased]`.

5. If no meaningful (user-facing) changes are found, do not modify the changelog.

6. Rules:
   - Write in English
   - Use imperative mood ("Add...", "Fix...", "Update...")
   - Keep entries concise but descriptive
   - Append new entries at the **end** of the relevant category
   - Do not modify entries under versioned sections (e.g., `## [1.6.0]`)

## Platform Detection

- `app/android/` → Android
- `app/ios/` → iOS
- `**/androidMain/**` → Android
- `**/iosMain/**` → iOS
- `kmp/`, `compose/`, `libraries/`, `shared/` (under `kmp/`) → Both (unless matched by a platform-specific rule above)
- `build-logic/`, `gradle/`, `settings.gradle.kts` → skip, except changes to `minSdk`, `compileSdk`, or `targetSdk` in version catalogs, which are user-facing → Changed
- `config/`, `docs/`, `README.md`, `CLAUDE.md` → skip

## Examples

- `### Changed` — Raise minimum Android SDK from 23 to 24
- `### Fixed` — Fix crash when in-app update is triggered while another update is already in progress
- `### Added` — Add platform filter to changelog screen

$ARGUMENTS
