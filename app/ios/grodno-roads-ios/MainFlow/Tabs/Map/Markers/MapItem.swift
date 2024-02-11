//
//  MapItem.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 10.02.24.
//

import GoogleMaps
import Root

public typealias LocationCoordinate = CLLocationCoordinate2D

enum MapEventType {
    case camera(MapCameraMarker)
    case report(MapReportMarker)
}

enum MapEventCameraType {
    case stationary(Int32)
    case mobile(Int32)
    case medium(Int32)
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
    
    private let event: MapEvent
    
    init?(event: MapEvent) {
        self.event = event
        
        switch event {
        case let event as MapEventCameraMediumSpeedCamera:
            self.id = event.id
            let marker = MapCameraMarker(
                cameraType: .medium(event.speedCar),
                position: event.locationCoordinate
            )
            self.eventType = .camera(marker)
            marker.mapItem = self
        case let event as MapEventCameraMobileCamera:
            self.id = event.id
            let marker = MapCameraMarker(
                cameraType: .mobile(event.speedCar),
                position: event.locationCoordinate
            )
            self.eventType = .camera(marker)
            marker.mapItem = self
        case let event as MapEventCameraStationaryCamera:
            self.id = event.id
            let marker = MapCameraMarker(
                cameraType: .stationary(event.speedCar),
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

extension MapEvent {
    var locationCoordinate: LocationCoordinate {
        position.asLocationCoordinate
    }
}

extension LatLng {
    var asLocationCoordinate: LocationCoordinate {
        LocationCoordinate(latitude: self.latitude, longitude: self.longitude)
    }
}
