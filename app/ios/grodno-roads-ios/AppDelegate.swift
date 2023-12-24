//
//  AppDelegate.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 23.12.23.
//

import FirebaseCore
import GoogleMaps
import Root
import UIKit

class AppDelegate: NSObject, UIApplicationDelegate {
    
    private(set) lazy var root: RootComponent = RootComponentImpl(componentContext: .context())
    
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        setupGoogleMaps()
        FirebaseApp.configure()
        KoinHelper_iosKt.doInitKoin()
        return true
    }
    
    private func setupGoogleMaps() {
        guard
            let location = Bundle.main.url(forResource: "secrets", withExtension: "properties"),
            let value = try? String(contentsOf: location).components(separatedBy: "=").last
        else {
            return
        }
        
        GMSServices.provideAPIKey(value)
    }
}
