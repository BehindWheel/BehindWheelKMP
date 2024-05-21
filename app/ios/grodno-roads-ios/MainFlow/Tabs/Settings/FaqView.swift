//
//  FaqView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 21.12.23.
//

import SwiftUI
import Root

struct FaqScreenView: View {
    private let component: FaqComponent
    
    @StateValue
    private var state: FaqStoreState
    
    private let onBack: (() -> Void)
    
    init(_ component: FaqComponent, onBack: @escaping (() -> Void)) {
        self.component = component
        _state = StateValue(component.state)
        self.onBack = onBack
    }
    
    var body: some View {
        ZStack {
            ScrollView {
                LazyVStack(spacing: 16) {
                    ForEach(state.faq) { faq in
                        FaqView(question: faq.question, answer: faq.answer)
                    }
                }
                .padding(.horizontal, 16)
            }
            if state.isLoading {
                LoadingView()
            }
        }
        .navigation(title: "FAQ", onBack: onBack)
    }
}

private struct FaqView: View {
    var question: String
    var answer: String
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 10)
                .fill(.white)
                .shadow(radius: 2)
            
            VStack(alignment: .leading) {
                Text(question)
                    .font(.headline)
                    .bold()
                    .padding([.bottom], 4)
                    .frame(maxWidth: .infinity, alignment: .leading)
                
                Text(answer)
                    .font(.body)
            }
            .padding()
        }
        .fixedSize(horizontal: false, vertical: true)
    }
}

extension FAQ: Identifiable {}

#Preview {
    FaqScreenView(FaqComponentPreview(), onBack: {})
}
