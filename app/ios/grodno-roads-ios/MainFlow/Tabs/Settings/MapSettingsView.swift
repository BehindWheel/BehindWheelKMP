//
//  MapSettingsView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 23.12.23.
//

import SwiftUI
import Root

struct MapSettingsView: View {
    private let component: MapSettingsComponent
    private let onBack: (() -> Void)
    
    init(_ component: MapSettingsComponent, onBack: @escaping (() -> Void)) {
        self.component = component
        self.onBack = onBack
    }
    
    var body: some View {
        VStack {
            Text("MapSettingsView")
        }
        .navigation(title: "Map", onBack: onBack)
    }
}

#Preview {
    MapSettingsView(MapSettingsComponentPreview()) {}
}
