# Changelog (iOS)

## [Unreleased]

### Added

- Add user count tooltip
- Add "Confirm" button in marker info dialog to quickly report that road events are still active
- Show markers as simple colored circles when map is zoomed far out, instead of detailed icons

### Fixed

- Fix missing horizontal padding on title in marker info bottom sheet

### Changed

- Filter `Camera` alerts by road corridor: suppress alerts when user is on a parallel
  road (cross-track distance > 40 m from camera axis)
- Introduce compact navigation bar
- Add more speed limits for Mobile cameras
- Update city areas JSON
- Bump min supported iOS 16
- Migrate from Cocoapods to SPM
- Update Google Maps to 10.13.0
- Update medium speed camera color
- Merge mobile cameras that are close to each other (within 300 m) into a single marker

