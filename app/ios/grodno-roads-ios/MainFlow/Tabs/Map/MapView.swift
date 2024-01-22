//
//  MapView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 20.12.23.
//

import SwiftUI
import Root

struct MapView: View {
    private let component: GuidanceComponent
    
    init(_ component: GuidanceComponent) {
        self.component = component
    }
    
    var body: some View {
        MapViewControllerBridge(component)
    }
}
