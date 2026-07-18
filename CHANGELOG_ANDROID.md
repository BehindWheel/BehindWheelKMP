# Changelog (Android)

## [Unreleased]

### Added

- Add "Drone video control" and "Motorcycle unit" reporting options for traffic police
- Add platform filter (Android/iOS) to changelog screen

### Fixed

- Fix crash when in-app update is triggered while another update is already in progress
- Fix zoom to current location button not accounting for bearing in Default mode

### Changed

- Add analytics tracking for in-app update errors
- Add error and empty state handling with retry in changelog screen

## [1.7.0] - 2026-07-14

### Added

- Dynamic map zoom depending on speed
- Add compass button to rotate map north when map is rotated
- Group cities by region with sticky headers in city selection list

### Fixed

- Fix map briefly rotating to north and resetting tilt when rotating screen during navigation
- Fix Apache HTTP legacy crash on Android 10 devices

### Changed

- Show markers as simple colored circles when map is zoomed far out, instead of detailed icons
- Update medium speed camera color
- Merge mobile cameras that are close to each other (within 300 m) into a single marker
- Dynamic map zoom that adjusts to the selected city's default zoom level
- Adjust map overlay depending on screen size

## [1.6.0] - 2026-06-27

### Added

- Add "Confirm" button in marker info dialog to quickly report that road events are still active

### Fixed

- Fix missing horizontal padding on title in marker info bottom sheet
- Fix list not scrolling to selected city when it is off-screen

### Changed

- Filter `Camera` alerts by road corridor: suppress alerts when user is on a parallel road (cross-track distance > 40 m
  from camera axis)
- Introduce compact navigation bar
- Add more speed limits for Mobile cameras
- Update city areas JSON
- Migrate alerts to `Media3` library

