# Changelog (Android)

## [Unreleased]

### Added

- Add compass button to rotate map north when map is rotated

### Changed

- Show markers as simple colored circles when map is zoomed far out, instead of detailed icons
- Update medium speed camera color
- Merge mobile cameras that are close to each other (within 300 m) into a single marker

## [1.6.0] - 2026-06-27

### Added

- Add "Confirm" button in marker info dialog to quickly report that road events are still active

### Fixed

- Fix missing horizontal padding on title in marker info bottom sheet

### Changed

- Filter `Camera` alerts by road corridor: suppress alerts when user is on a parallel
  road (cross-track distance > 40 m from camera axis)
- Introduce compact navigation bar
- Add more speed limits for Mobile cameras
- Update city areas JSON
- Migrate alerts to `Media3` library

