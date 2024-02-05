//
//  AlertsView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 22.12.23.
//

import SwiftUI
import Root

struct AlertsView: View {
    private let component: AlertsComponent
    private let onBack: (() -> Void)
    
    init(_ component: AlertsComponent, onBack: @escaping (() -> Void)) {
        self.component = component
        self.onBack = onBack
    }
    
    var body: some View {
        VStack {
            Text("AlertsView")
        }
        .navigation(title: "Alerts", onBack: onBack)
    }
}

#Preview {
    AlertsView(AlertsComponentPreview()) {}
}
