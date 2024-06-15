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
    
    @CFlowValue
    private var state: MapSettingsComponentMapSettingState!
    
    init(_ component: MapSettingsComponent, onBack: @escaping (() -> Void)) {
        self.component = component
        self.onBack = onBack
        
        _state = CFlowValue(component.state)
    }
    
    var body: some View {
        let trafficEnabled = Binding<Bool>(
            get: { state.mapSettings.mapInfo.trafficJam.isShow },
            set: { component.modify(preference__: MapSettingsComponentMapPrefTrafficJam(isShow: $0)) }
        )
        
        VStack {
            Toggle("map_traffic_conditions_appearance".localized, isOn: trafficEnabled)
                .font(.system(size: 16, weight: .medium))
                .foregroundStyle(.black.opacity(0.75))
                .padding()
                .onChange(of: trafficEnabled.wrappedValue) { newValue in
                    trafficEnabled.wrappedValue = newValue
                }
            Spacer()
        }
        .navigation(title: "settings_section_map".localized, onBack: onBack)
    }
}

#Preview {
    MapSettingsView(MapSettingsComponentPreview()) {}
}
