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
            self.icon = MR.images().ic_map_stationary_camera.asUIImage()
            self.setIconSize(scaledToSize: .init(width: 26, height: 29))
        case .medium:
            self.icon = MR.images().ic_map_medium_speed_camera.asUIImage()
            self.setIconSize(scaledToSize: .init(width: 26, height: 29))
        case .mobile:
            self.icon = MR.images().ic_map_mobile_camera.asUIImage()
            self.setIconSize(scaledToSize: .init(width: 26, height: 29))
        case .unknown:
            self.icon = nil
        }
    }
}

extension GMSMarker {
    func setIconSize(scaledToSize newSize: CGSize) {
        UIGraphicsBeginImageContextWithOptions(newSize, false, 0.0)
        icon?.draw(in: CGRect(x: 0, y: 0, width: newSize.width, height: newSize.height))
        let newImage: UIImage = UIGraphicsGetImageFromCurrentImageContext()!
        UIGraphicsEndImageContext()
        icon = newImage
    }
}
