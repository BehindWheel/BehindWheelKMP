//
//  MapItem.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 10.02.24.
//

import GoogleMaps
import Root

enum MapEventType {
    case camera(MapCameraMarker)
    case report(MapReportMarker)
}

class MapItem: Equatable {
    
    private let id: String
    private let eventType: MapEventType
    
    var marker: GMSMarker {
        switch eventType {
        case .camera(let marker):
            return marker
        case .report(let marker):
            return marker
        }
    }
    
    let event: MapEvent
    
    init?(event: MapEvent) {
        self.event = event
        
        switch event {
        case let event as MapEventCameraMediumSpeedCamera:
            self.id = event.id
            let marker = MapCameraMarker(
                cameraType: .medium,
                position: event.locationCoordinate
            )
            self.eventType = .camera(marker)
            marker.mapItem = self
        case let event as MapEventCameraMobileCamera:
            self.id = event.id
            let marker = MapCameraMarker(
                cameraType: .mobile,
                position: event.locationCoordinate
            )
            self.eventType = .camera(marker)
            marker.mapItem = self
        case let event as MapEventCameraStationaryCamera:
            self.id = event.id
            let marker = MapCameraMarker(
                cameraType: .stationary,
                position: event.locationCoordinate
            )
            self.eventType = .camera(marker)
            marker.mapItem = self
        case let event as MapEventReports:
            self.id = event.id
            let marker = MapReportMarker(
                message: event.markerMessage,
                position: event.locationCoordinate
            )
            self.eventType = .report(marker)
            marker.mapItem = self
        default:
            return nil
        }
    }
    
    static func == (lhs: MapItem, rhs: MapItem) -> Bool {
        return lhs.id == rhs.id
    }
}
