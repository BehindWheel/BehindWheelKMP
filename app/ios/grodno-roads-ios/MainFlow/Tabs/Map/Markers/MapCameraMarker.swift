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
        case .stationary(let speed):
            self.icon = UIImage(systemName: "camera.fill")
            self.title = "Стационарная камера: \(speed)"
        case .medium(let speed):
            self.icon =  UIImage(systemName: "camera.on.rectangle.fill")
            self.title = "Камера средней скорости: \(speed)"
        case .mobile(let speed):
            self.icon = UIImage(systemName: "camera.rotate.fill")
            self.title = "Мобильная камера: \(speed)"
        }
    }
}
