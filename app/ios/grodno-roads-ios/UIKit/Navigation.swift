//
//  Navigation.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 20.01.24.
//

import SwiftUI

struct Navigation: ViewModifier {
    
    private let title: String
    private var onBack: (() -> Void)?
    
    init(title: String, onBack: (() -> Void)?) {
        self.title = title
        self.onBack = onBack
    }
    
    func body(content: Content) -> some View {
        NavigationView {
            content
                .navigationBarHidden(false)
                .navigationTitle(title)
                .navigationBarTitleDisplayMode(.inline)
                .toolbar {
                    ToolbarItem(placement: .topBarLeading) {
                        Button {
                            onBack?()
                        } label: {
                            Image(systemName: "chevron.backward").foregroundStyle(.black)
                        }
                    }
                }
        }
    }
}

extension View {
    func navigation(title: String, onBack: (() -> Void)?) -> some View {
        modifier(Navigation(title: title, onBack: onBack))
    }
}
