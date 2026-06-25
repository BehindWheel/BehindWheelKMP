# Changelog (iOS)

## [Unreleased]

### Added

- Add user count tooltip
- Add "Confirm" button in marker info dialog to quickly report that road events are still active

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

