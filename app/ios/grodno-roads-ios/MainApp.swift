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
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
