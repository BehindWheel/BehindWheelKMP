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
        VStack(alignment: .leading, spacing: 8) {
            Text("Main")
                .bold()
                .frame(maxWidth: .infinity, alignment: .leading)
            Button("Appearance") {
                component.open(page: .appearance)
            }
            .padding([.leading], 12)
            .padding([.top], 6)
            Button("Map settings") {
                component.open(page: .mapsettings)
            }
            .padding([.leading], 12)
            .padding([.top], 6)
            Button("Alerts") {
                component.open(page: .alerts)
            }
            .padding([.leading], 12)
            .padding([.top, .bottom], 6)
            Text("Other")
                .bold()
                .frame(maxWidth: .infinity, alignment: .leading)
            Button("Changelog") {
                component.open(page: .changelog)
            }
            .padding([.leading], 12)
            .padding([.top], 6)
            Button("FAQ") {
                component.open(page: .faq)
            }
            .padding([.leading], 12)
            .padding([.top], 6)
            Spacer()
            Text("Version: \(component.appVersion)")
                .bold()
                .frame(maxWidth: .infinity, alignment: .center)
                .padding()
        }
        .padding()
        .frame(maxHeight: .infinity)
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
