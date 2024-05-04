//
//  MapEventCameraType.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 18.02.24.
//

import Foundation

enum MapEventCameraType {
    case stationary
    case mobile
    case medium
    case unknown
    
    var title: String {
        switch self {
        case .stationary:
            "alerts_stationary_camera".localized
        case .mobile:
            "alerts_mobile_camera".localized
        case .medium:
            "alerts_medium_speed_camera".localized
        case .unknown:
            ""
        }
    }
}
