//
//  View.swift
//  grodno-roads-ios
//
//  Created by Yahor Urbanovich on 7.05.24.
//

import SwiftUI

extension View {
    @ViewBuilder func alert<T, A>(
        item: T?,
        onDismiss: @escaping (T) -> Void,
        title: (T) -> Text,
        message: (T) -> Text,
        actions: (T) -> A
    ) -> some View where A : View {
        if let item = item {
            alert(
                title(item),
                isPresented: Binding(get: { true }, set: {_,_ in onDismiss(item) }),
                actions: { actions(item) },
                message: { message(item) }
            )
        } else {
            self
        }
    }
 
    func sheet<T, Content>(
        item: T?,
        onDismiss: @escaping () -> Void,
        @ViewBuilder content: @escaping (T) -> Content
    ) -> some View where Content: View {
        sheet(
            isPresented: .init(
                get: { item != nil },
                set: { _ in }
            ),
            onDismiss: onDismiss,
            content: { content(item!) }
        )
    }
}
