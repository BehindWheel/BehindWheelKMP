//
//  SettingsItem.swift
//  grodno-roads-ios
//
//  Created by Yahor Urbanovich on 24.12.23.
//

import SwiftUI

struct SettingsItemView : View {
    private let leadingIcon: String
    private let headlineText: String
    private let action: (() -> Void)
    
    init(leadingIcon: String, headlineText: String, action: @escaping () -> Void) {
        self.leadingIcon = leadingIcon
        self.headlineText = headlineText
        self.action = action
    }
    
    var body: some View {
        Button {
            action()
        } label: {
            HStack(spacing: 16) {
                Image(systemName: leadingIcon)
                    .foregroundColor(Color.black.opacity(0.6))
                Text(headlineText).font(.system(size: 16, weight: .medium))
                Spacer()
            }
            .contentShape(Rectangle())
        }
        .buttonStyle(.plain)
        .frame(maxWidth: .infinity, minHeight: 48)
    }
}

#Preview {
    SettingsItemView(leadingIcon: "map.fill", headlineText: "Map", action: {})
    .background(Color.gray.opacity(0.2))
}
