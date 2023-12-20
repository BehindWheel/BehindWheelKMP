//
//  AppSettings.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 20.12.23.
//

import SwiftUI
import Root

struct AppSettings: View {
    let component: AppSettingsComponent
    
    init(_ component: AppSettingsComponent) {
        self.component = component
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Main").bold().frame(maxWidth: .infinity, alignment: .leading)
            Button("Appearance") {
                component.open(page: .appearance)
            }.padding()
            Button("Map") {
                component.open(page: .map)
            }
            .padding()
            .foregroundStyle(.red)
            Button("Alerts") {
                component.open(page: .alerts)
            }
            .padding()
            .foregroundStyle(.red)
            Text("Other").bold().frame(maxWidth: .infinity, alignment: .leading)
            Button("Changelog") {
                component.open(page: .changelog)
            }.padding()
            Button("FAQ") {
                component.open(page: .faq)
            }.padding()
            Text("Version: \(component.appVersion)").bold().frame(maxWidth: .infinity, alignment: .leading)
        }
        .padding()
        .frame(maxHeight: .infinity)
    }
}
