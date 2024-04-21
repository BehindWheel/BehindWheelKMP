//
//  SettingsRoundItem.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 5.01.24.
//

import SwiftUI

struct SettingsRoundItem: View {
    
    private let title: String
    private let image: String
    private let action: (() -> Void)
    
    init(_ title: String, image: String, action: @escaping () -> Void) {
        self.title = title
        self.image = image
        self.action = action
    }
    
    var body: some View {
        Button {
            action()
        } label: {
            VStack(spacing: 8) {
                Image(systemName: image)
                    .padding(.all, 8)
                    .frame(width: 48, height: 48)
                    .background(.white)
                    .clipShape(.rect(cornerRadius: 24))
                    .overlay(alignment: .center, content: {
                        RoundedRectangle(cornerRadius: 24)
                            .stroke(.gray, lineWidth: 1)
                    })
                Text(title).font(.system(size: 11, weight: .medium))
            }
            .contentShape(Rectangle())
        }
        .tint(.black)
    }
}

#Preview {
    SettingsRoundItem("Chat", image: "questionmark.circle.fill") {}
}
