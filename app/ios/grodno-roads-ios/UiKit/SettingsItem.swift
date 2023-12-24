//
//  SettingsItem.swift
//  grodno-roads-ios
//
//  Created by Yahor Urbanovich on 24.12.23.
//

import SwiftUI

struct SettingsItemView : View {
    var leadingIcon: String
    var headlineText: String
    
    var body: some View {
        HStack {
            Image(systemName: leadingIcon)
            Text(headlineText)
            Spacer()
            Image(systemName: "chevron.forward")
                .padding()
        }
        .frame(maxWidth: .infinity, maxHeight: 48)
        .contentShape(Rectangle())
    }
}

#Preview {
    SettingsItemView(leadingIcon: "map.fill",
                     headlineText: "Test")
    .background(Color.gray.opacity(0.2))
}
