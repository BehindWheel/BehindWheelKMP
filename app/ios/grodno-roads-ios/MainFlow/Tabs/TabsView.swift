//
//  TabsView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 18.12.23.
//

import SwiftUI
import Root

struct TabsView: View {
    private let component: TabsComponent
    
    @StateValue
    private var childStack: ChildStack<AnyObject, TabsComponentChild>
    
    private var activeChild: TabsComponentChild { childStack.active.instance }
    
    init(_ component: TabsComponent) {
        self.component = component
        _childStack = StateValue(component.childStack)
    }
    
    var body: some View {
        VStack {
            ChildView(child: activeChild)
                .frame(maxHeight: .infinity)
            HStack(alignment: .bottom, spacing: 16) {
                Button(action: { component.onSelectTab(index: 0) }) {
                    Label("Map", systemImage: "map.fill")
                        .labelStyle(VerticalLabelStyle())
                        .opacity(activeChild is TabsComponentChild.Map ? 1 : 0.5)
                }
                .frame(maxWidth: .infinity)
                Button(action: { component.onSelectTab(index: 1) }) {
                    Label("Settings", systemImage: "gearshape.fill")
                        .labelStyle(VerticalLabelStyle())
                        .opacity(activeChild is TabsComponentChild.AppSettings ? 1 : 0.5)
                }
                .frame(maxWidth: .infinity)
            }
        }
    }
}

private struct ChildView: View {
    let child: TabsComponentChild
    
    var body: some View {
        switch child {
        case let child as TabsComponentChild.Map: MapView()
        case let child as TabsComponentChild.AppSettings: AppSettings(child.component)
        default: EmptyView()
        }
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
