//
//  NavigationBarItem.swift
//  grodno-roads-ios
//
//  Created by Yahor Urbanovich on 26.04.24.
//

import SwiftUI
import Foundation
import Root

struct NavigationBarItem: View {
    let text: String
    let image: UIImage
    let alpha: Double
    
    init(text: String, image: UIImage, alpha: Double) {
        self.text = text
        self.image = image
        self.alpha = alpha
    }
    
    var body: some View {
        Label {
            Text(text)
        } icon: {
            Image(uiImage: image)
        }
        .labelStyle(VerticalLabelStyle())
        .opacity(alpha)
    }
}

private struct VerticalLabelStyle: LabelStyle {
    func makeBody(configuration: Configuration) -> some View {
        VStack(alignment: .center, spacing: 8) {
            configuration.icon
            configuration.title
        }
    }
}

#Preview {
    HStack(spacing: 16) {
        NavigationBarItem(
            text: MR.strings().tab_map.localize(),
            image: MR.images().ic_map.asUIImage(),
            alpha: 1)
        
        NavigationBarItem(
            text: MR.strings().tab_map.localize(),
            image: MR.images().ic_map.asUIImage(),
            alpha: 0.3)
    }
}
