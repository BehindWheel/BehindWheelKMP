# Changelog (iOS)

## [Unreleased]

### Added

- Add compass button to rotate map north when map is rotated
- Add user count tooltip
- Add "Confirm" button in marker info dialog to quickly report that road events are still active
- Show markers as simple colored circles when map is zoomed far out, instead of detailed icons
- Group cities by region with sticky headers in city selection list

### Fixed

- Fix missing horizontal padding on title in marker info bottom sheet
- Fix list not scrolling to selected city when it is off-screen

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
- Dynamic map zoom that adjusts to the selected city's default zoom level

