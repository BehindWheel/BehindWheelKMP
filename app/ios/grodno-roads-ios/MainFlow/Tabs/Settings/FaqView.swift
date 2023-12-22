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
    
    @StateFlow
    private var state: FaqStoreState
    
    init(_ component: FaqComponent) {
        self.component = component
        _state = StateFlow(component.state)
    }
    
    var body: some View {
        ScrollView {
            VStack {
                Text("FAQ").padding()
                Divider()
                ForEach(state.faq) { faq in
                    Text(faq.question)
                        .bold()
                        .padding([.vertical], 4)
                        .frame(alignment: .center)
                    Text(faq.answer)
                        .padding([.horizontal], 8)
                    Divider()
                }
            }
        }
    }
}

extension FAQ: Identifiable {}

#Preview {
    FaqView(
        FaqComponentImplKt.buildFaqComponent(
            componentContext: .context()
        )
    )
}
