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
    private let onBack: (() -> Void)
    
    @StateValue
    private var state: ChangelogStoreState
    
    init(_ component: ChangelogComponent, onBack: @escaping (() -> Void)) {
        self.component = component
        _state = StateValue(component.state)
        self.onBack = onBack
    }
    
    var body: some View {
        ScrollView {
            VStack {
                Text("ChangelogView").padding()
                Divider()
                ForEach(state.releaseNotes) { notes in
                    Text(notes.notes)
                    Divider()
                }
            }
        }
        .navigation(title: "Changelog", onBack: onBack)
    }
}

extension ReleaseNotes: Identifiable {}

#Preview {
    ChangelogView(ChangelogComponentPreview()) {}
}
