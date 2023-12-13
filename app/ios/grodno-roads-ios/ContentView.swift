//
//  ContentView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 3.12.23.
//

import SwiftUI
import test_shared

struct ContentView: View {
    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundStyle(.tint)
            Text(Greeting().greet())
        }
        .padding()
    }
}

#Preview {
    ContentView()
}
