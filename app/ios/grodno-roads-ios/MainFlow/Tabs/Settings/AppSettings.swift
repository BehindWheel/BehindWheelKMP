//
//  AppSettings.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 20.12.23.
//

import SwiftUI
import Root

struct AppSettings: View {
    private let component: AppSettingsComponent
    
    init(_ component: AppSettingsComponent) {
        self.component = component
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            SettingsHeaderView(title: "Main")
            
            Button {
                component.open(page: .appearance)
            } label: {
                SettingsItemView(leadingIcon: "swatchpalette.fill", headlineText: "Appearance")
            }
            .buttonStyle(.plain)
            
            Button {
                component.open(page: .mapsettings)
            } label: {
                SettingsItemView(leadingIcon: "map.fill", headlineText: "Map")
            }
            .buttonStyle(.plain)
            
            Button {
                component.open(page: .alerts)
            } label: {
                SettingsItemView(leadingIcon: "speaker.wave.2.bubble.fill", headlineText: "Alerts")
            }
            .buttonStyle(.plain)
            
            SettingsHeaderView(title: "Other")
                .padding(.top, 16)
            
            Button {
                component.open(page: .changelog)
            } label: {
                SettingsItemView(leadingIcon: "newspaper.fill", headlineText: "Changelog")
            }
            .buttonStyle(.plain)
            
            Button {
                component.open(page: .faq)
            } label: {
                SettingsItemView(leadingIcon: "quote.bubble.fill", headlineText: "FAQ")
            }
            .buttonStyle(.plain)
            
            Spacer()
            Text("Version: \(component.appVersion)")
                .bold()
                .frame(maxWidth: .infinity, alignment: .center)
                .padding()
        }
        .padding()
    }
}

#Preview {
    AppSettings(
        AppSettingsComponentImplKt.buildAppSettingsComponent(
            componentContext: .context(),
            onOpen: { _ in }
        )
    )
}
