//
//  MainApp.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 3.12.23.
//

import SwiftUI
import Firebase
import Root

class AppDelegate: NSObject, UIApplicationDelegate {
    
    private(set) lazy var root: RootComponent = RootComponentImpl(
        componentContext: DefaultComponentContext(lifecycle: ApplicationLifecycle())
    )
    
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        FirebaseApp.configure()
        KoinHelper_iosKt.doInitKoin()
        return true
    }
    
}

@main
struct MainApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var delegate
    
    var body: some Scene {
        WindowGroup {
            RootView(root: delegate.root)
        }
    }
}
