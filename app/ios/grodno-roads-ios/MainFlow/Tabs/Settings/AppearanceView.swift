//
//  AppearanceView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 18.12.23.
//

import SwiftUI
import Root

struct AppearanceView: View {
    private let component: AppearanceComponent
    private let onBack: (() -> Void)
    
    init(_ component: AppearanceComponent, onBack: @escaping (() -> Void)) {
        self.component = component
        self.onBack = onBack
    }
    
    var body: some View {
        VStack {
            Text("AppearanceView")
        }
        .navigation(title: "Appearance", onBack: onBack)
    }
}

#Preview {
    AppearanceView(AppearanceComponentPreview()) {}
}
