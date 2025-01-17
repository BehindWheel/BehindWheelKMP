//
//  RootView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 18.12.23.
//

import SwiftUI
import Root

struct RootView: UIViewControllerRepresentable {
    let root: RootComponent
    let backDispatcher: BackDispatcher
    
    func makeUIViewController(context: Context) -> UIViewController {
           let controller = RootViewController().create(rootComponent: root,
                                                        backDispatcher: backDispatcher)
           return controller
       }

       func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
       }
}
