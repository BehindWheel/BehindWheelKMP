//
//  RootView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 18.12.23.
//

import SwiftUI
import Root

struct RootView: View {
    private let root: RootComponent
    
    init(root: RootComponent) {
        self.root = root
    }

    var body: some View {
        StackView(
            stackValue: ObservableState(root.childStack),
            childContent: { child in
                switch child {
                case let child as RootComponentChild.MainFlow: MainFlowView(child.component)
                case let child as RootComponentChild.Onboarding: OnboardingView(child.component)
                default: EmptyView()
                }
            },
            onBack: {}
        )
    }
}
