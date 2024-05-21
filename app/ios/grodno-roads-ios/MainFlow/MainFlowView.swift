//
//  MainFlowView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 18.12.23.
//

import SwiftUI
import Root

struct MainFlowView: View {
    private let component: MainFlowComponent
    
    init(_ component: MainFlowComponent) {
        self.component = component
    }
    
    var body: some View {
        StackView(
            stackValue: ObservableState(component.childStack),
            childContent: { child in
                switch child {
                case let child as MainFlowComponentChild.Alerts: AlertsView(child.component) { component.onBack() }
                case let child as MainFlowComponentChild.Appearance: AppearanceView(child.component) { component.onBack() }
                case let child as MainFlowComponentChild.Changelog: ChangelogView(child.component) { component.onBack() }
                case let child as MainFlowComponentChild.FAQ: FaqScreenView(child.component) { component.onBack() }
                case let child as MainFlowComponentChild.MapSettings: MapSettingsView(child.component) { component.onBack() }
                case let child as MainFlowComponentChild.Tabs: TabsView(child.component)
                default: EmptyView()
                }
            }, 
            onBack: component.onBack
        )
    }
}
