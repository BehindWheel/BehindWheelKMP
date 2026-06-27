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

### 3. Done

The CI workflow (`release_aab.yml`) will automatically:
- Read the version from `android.properties`
- Create a git tag `android/vX.Y.Z`
- Build and sign the release AAB
- Deploy to Play Store (beta track)
- Create a GitHub Release with auto-generated changelog from PR labels (configured in `.github/release.yml`)

