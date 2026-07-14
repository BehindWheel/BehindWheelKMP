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

5. Rules:
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
- `build-logic/`, `gradle/`, `settings.gradle.kts` → skip (build configuration, not user-facing)

$ARGUMENTS
