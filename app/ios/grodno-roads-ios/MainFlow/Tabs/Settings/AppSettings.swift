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
        Text("AppSettings")
    }
}
