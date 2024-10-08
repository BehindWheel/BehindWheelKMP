//
//  MapCameraMarker.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 10.02.24.
//

import GoogleMaps
import Root

class MapCameraMarker: GMSMarker, MapMarkerProtocol {
    
    weak var mapItem: MapItem?
    
    init(cameraType: MapEventCameraType, position: LocationCoordinate) {
        super.init()
        self.position = position
        
        switch cameraType {
        case .stationary:
            self.icon = MR.images().nt_ic_marker_stationary_camera.asUIImage()
        case .medium:
            self.icon = MR.images().nt_ic_marker_medium_speed_camera.asUIImage()
        case .mobile:
            self.icon = MR.images().nt_ic_marker_mobile_camera.asUIImage()
        case .unknown:
            self.icon = nil
        }
    }
}
