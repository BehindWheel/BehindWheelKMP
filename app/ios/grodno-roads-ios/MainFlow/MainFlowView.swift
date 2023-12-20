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
            stackValue: StateValue(component.childStack),
            getTitle: { child in
                switch child {
                case is MainFlowComponentChild.Appearance: "Appearance"
                case is MainFlowComponentChild.Changelog: "Changelog"
                case is MainFlowComponentChild.Tabs: "Tabs"
                default: "Default"
                }
            },
            onBack: { _ in },
            childContent: { child in
                switch child {
                case _ as MainFlowComponentChild.Appearance: AppearanceView()
                case let child as MainFlowComponentChild.Changelog: ChangelogView(child.component)
                case let child as MainFlowComponentChild.Tabs: TabsView(child.component)
                default: EmptyView()
                }
            }
        )
    }
}
