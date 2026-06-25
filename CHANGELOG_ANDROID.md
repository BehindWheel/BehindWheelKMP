# Changelog (Android)

## [Unreleased]

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

