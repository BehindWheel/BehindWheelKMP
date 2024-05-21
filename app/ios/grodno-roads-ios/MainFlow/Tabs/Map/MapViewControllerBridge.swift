//
//  MapViewController.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 25.12.23.
//

import SwiftUI
import Root

struct MapViewControllerBridge: UIViewControllerRepresentable {
    
    private let component: GuidanceComponent
    
    init(_ component: GuidanceComponent) {
        self.component = component
    }
    
    func makeUIViewController(context: Context) -> MapViewController {
        MapViewController(component)
    }
    
    func updateUIViewController(_ uiViewController: MapViewController, context: Context) {
    }
}

