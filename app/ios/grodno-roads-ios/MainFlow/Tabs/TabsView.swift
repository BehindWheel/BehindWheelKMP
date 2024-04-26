//
//  TabsView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 18.12.23.
//

import SwiftUI
import Root

struct TabsView: View {
    
    private enum Constants {
        static let cornerRadius: CGFloat = 28
        static let verticalInset: CGFloat = 12
        static let buttonsHeight: CGFloat = 48
    }
    
    private let component: TabsComponent
    
    @StateValue
    private var childStack: ChildStack<AnyObject, TabsComponentChild>
    
    private var activeChild: TabsComponentChild { childStack.active.instance }
    
    init(_ component: TabsComponent) {
        self.component = component
        _childStack = StateValue(component.childStack)
    }
    
    var body: some View {
        GeometryReader { geometry in
            ZStack(alignment: .bottom) {
                ChildView(child: activeChild)
                    .frame(maxHeight: .infinity)
                VStack(spacing: 0) {
                    ZStack(alignment: .top) {
                        Image(.roundedTopView)
                            .resizable()
                            .frame(maxWidth: .infinity, maxHeight: Constants.cornerRadius)
                            .shadow(radius: 1)
                        Color(.white)
                            .offset(y: Constants.cornerRadius)
                            .frame(height: Constants.buttonsHeight)
                        HStack(spacing: 16) {
                            Button(action: { component.onSelectTab(index: 0) }) {
                                NavigationBarItem(
                                    text: MR.strings().tab_map.desc().localized(),
                                    image: MR.images().ic_map.toUIImage()!,
                                    alpha: activeChild is TabsComponentChild.Guidance ? 1 : 0.5)
                            }
                            .tint(.black)
                            .frame(maxWidth: .infinity)
                            Button(action: { component.onSelectTab(index: 1) }) {
                                NavigationBarItem(
                                    text: MR.strings().tab_settings.desc().localized(),
                                    image: MR.images().ic_settings.toUIImage()!,
                                    alpha: activeChild is TabsComponentChild.AppSettings ? 1 : 0.5)
                            }
                            .tint(.black)
                            .frame(maxWidth: .infinity)
                        }
                        .offset(y: Constants.verticalInset)
                    }
                    .frame(height: Constants.cornerRadius + Constants.buttonsHeight)
                    Color(.white).frame(height: {
                        let bottomSafeArea = geometry.safeAreaInsets.bottom
                        let height = bottomSafeArea == 0 ? Constants.verticalInset : bottomSafeArea
                        return height
                    }())
                }
                .padding(.bottom, -geometry.safeAreaInsets.bottom)
            }
        }
    }
}

private struct ChildView: View {
    let child: TabsComponentChild
    
    var body: some View {
        switch child {
        case let child as TabsComponentChild.Guidance: MapView(child.component)
        case let child as TabsComponentChild.AppSettings: AppSettings(child.component)
        default: EmptyView()
        }
    }
}
