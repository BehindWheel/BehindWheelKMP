//
//  View.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 21.01.24.
//

import SwiftUI

extension View {
    @ViewBuilder
    func modify(@ViewBuilder _ transform: (Self) -> (some View)?) -> some View {
        if let view = transform(self), !(view is EmptyView) {
            view
        } else {
            self
        }
    }
}
