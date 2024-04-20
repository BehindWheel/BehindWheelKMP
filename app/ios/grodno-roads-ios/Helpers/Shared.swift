//
//  Shared.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 18.02.24.
//

import Root

extension MapEventCamera {
    var nativeCameraType: MapEventCameraType {
        if self is MapEventCameraStationaryCamera {
            .stationary
        } else if self is MapEventCameraMediumSpeedCamera {
            .medium
        } else if self is MapEventCameraMobileCamera {
            .mobile
        } else {
            .unknown
        }
    }
}

extension Source {
    var reportSource: ReportSource {
        if self == Source.app {
            .app
        } else if self == Source.viber {
            .viber
        } else if self == Source.telegram {
            .telegram
        } else if self == Source.zello {
            .zello
        } else {
            .unknown
        }
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
