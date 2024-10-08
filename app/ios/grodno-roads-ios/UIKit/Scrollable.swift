//
//  Scrollable.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 21.01.24.
//

import SwiftUI

// For iOS < 16
struct ScrollViewIfNeeded<Content: View>: View {
    @ViewBuilder let content: () -> Content

    @State private var scrollViewSize: CGSize = .zero
    @State private var contentSize: CGSize = .zero

    var body: some View {
        ScrollView(shouldScroll ? [.vertical] : []) {
            content().readSize($contentSize)
        }
        .readSize($scrollViewSize)
    }

    private var shouldScroll: Bool {
        scrollViewSize.height <= contentSize.height
    }
}

struct SizeReaderModifier: ViewModifier  {
    @Binding var size: CGSize
    
    func body(content: Content) -> some View {
        content.background(
            GeometryReader(content: { geometry in
                Color.clear.onAppear() {
                    DispatchQueue.main.async {
                         size = geometry.size
                    }
                }
            })
        )
    }
}

extension View {
    func readSize(_ size: Binding<CGSize>) -> some View {
        self.modifier(SizeReaderModifier(size: size))
    }
}


@available(iOS 16.0, *)
struct ViewThatFitsModifier: ViewModifier {
    private let axes: Axis.Set
    private let showsIndicators: Bool
    
    init(axes: Axis.Set, showsIndicators: Bool) {
        self.axes = axes
        self.showsIndicators = showsIndicators
    }
    
    func body(content: Content) -> some View {
        ViewThatFits(in: axes) {
            content
            ScrollView(axes, showsIndicators: showsIndicators) {
                content
            }
        }
    }
}

@available(iOS 16.0, *)
extension View {
    func viewThatFits(_ axes: Axis.Set, showsIndicators: Bool) -> some View {
        self.modifier(ViewThatFitsModifier(axes: axes, showsIndicators: showsIndicators))
    }
}


struct Scrollable: ViewModifier {
    private let axes: Axis.Set
    private let showsIndicators: Bool
    
    init(axes: Axis.Set, showsIndicators: Bool) {
        self.axes = axes
        self.showsIndicators = showsIndicators
    }
    
    func body(content: Content) -> some View {
        if #unavailable(iOS 16) {
            ScrollViewIfNeeded {
                content
            }
        } else {
            if #available(iOS 16.4, *) {
                ScrollView(axes, showsIndicators: showsIndicators) {
                    content
                }.scrollBounceBehavior(.basedOnSize, axes: axes)
            } else {
                content.viewThatFits(axes, showsIndicators: showsIndicators)
            }
        }
    }
}

extension View {
    func scrollable(axes: Axis.Set = .vertical, showsIndicators: Bool = false) -> some View {
        modifier(Scrollable(axes: axes, showsIndicators: showsIndicators))
    }
}
