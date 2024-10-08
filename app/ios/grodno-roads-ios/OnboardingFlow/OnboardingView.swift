//
//  OnboardingView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 18.12.23.
//

import SwiftUI
import Root

struct OnboardingView: View {
    private let component: OnboardingComponent
    
    init(_ component: OnboardingComponent) {
        self.component = component
    }
    
    var body: some View {
        OnboardingRepresentable(component: component)
    }
}

struct OnboardingRepresentable: UIViewControllerRepresentable {
    
    var component: OnboardingComponent
    
    func makeUIViewController(context: Context) -> UIViewController {
        return OnboardingViewController().create(onboardingComponent: component)
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}

#Preview {
    OnboardingView(
        OnboardingComponentImplKt.buildOnboardingComponent(
            componentContext: .context(),
            onFinishOnboarding: {}
        )
    )
}
