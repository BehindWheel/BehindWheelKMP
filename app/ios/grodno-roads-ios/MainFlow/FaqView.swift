//
//  FaqView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 21.12.23.
//

import SwiftUI
import Root

struct FaqView: View {
    private let component: FaqComponent
    
    init(_ component: FaqComponent) {
        self.component = component
    }
    
    var body: some View {
        VStack {
            Text("FAQ")
        }
    }
}

