//
//  AppDelegate.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 23.12.23.
//

import Firebase
import Root
import UIKit

class AppDelegate: NSObject, UIApplicationDelegate {
    
    private(set) lazy var root: RootComponent = RootComponentImpl(componentContext: .context())
    
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        FirebaseApp.configure()
        KoinHelper_iosKt.doInitKoin()
        return true
    }
}
