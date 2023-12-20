//
//  ChangelogView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 18.12.23.
//

import SwiftUI
import Root

struct ChangelogView: View {
    private let component: ChangelogComponent
    
    init(_ component: ChangelogComponent) {
        self.component = component
    }
    
    var body: some View {
        VStack {
            Text("ChangelogView")
        }
    }
}
