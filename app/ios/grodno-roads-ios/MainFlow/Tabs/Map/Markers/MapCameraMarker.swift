//
//  MapCameraMarker.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 10.02.24.
//

import GoogleMaps

class MapCameraMarker: GMSMarker, MapMarkerProtocol {
    
    weak var mapItem: MapItem?
    
    init(cameraType: MapEventCameraType, position: LocationCoordinate) {
        super.init()
        self.position = position
        
        switch cameraType {
        case .stationary:
            self.icon = UIImage(systemName: "camera.fill")
        case .medium:
            self.icon = UIImage(systemName: "camera.on.rectangle.fill")
        case .mobile:
            self.icon = UIImage(systemName: "camera.rotate.fill")
        case .unknown:
            self.icon = nil
        }
    }
}
