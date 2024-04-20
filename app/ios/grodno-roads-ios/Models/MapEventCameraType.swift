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
            "Стационарная камера"
        case .mobile:
            "Мобильная камера"
        case .medium:
            "Камера средней скорости"
        case .unknown:
            ""
        }
    }
}
