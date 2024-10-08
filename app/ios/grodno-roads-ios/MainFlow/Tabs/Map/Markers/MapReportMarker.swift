//
//  MapReportMarker.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 11.02.24.
//

import GoogleMaps

class MapReportMarker: GMSMarker, MapMarkerProtocol {

    weak var mapItem: MapItem?
    
    init(message: String, position: LocationCoordinate) {
        super.init()
        self.position = position
        
        self.icon = MapReportView(text: message).snapshot
    }
}
