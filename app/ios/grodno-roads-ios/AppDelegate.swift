//
//  AppDelegate.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 23.12.23.
//

import GoogleMaps
import Root
import UIKit

class AppDelegate: NSObject, UIApplicationDelegate {

    var backDispatcher: BackDispatcher = BackDispatcherKt.BackDispatcher()

    private(set) lazy var root: RootComponent = RootComponentKt.buildRootComponent(
        componentContext: DefaultComponentContext(
            lifecycle: ApplicationLifecycle(),
            stateKeeper: nil,
            instanceKeeper: nil,
            backHandler: backDispatcher
        )
    )

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        FirebaseInit.shared.start()
        KoinInit.shared.start()

        setupGoogleMaps()
        return true
    }
}

extension AppDelegate {
    private func setupGoogleMaps() {
        guard
            let location = Bundle.main.url(forResource: "secrets", withExtension: "properties"),
            let lines = try? String(contentsOf: location).components(separatedBy: .newlines),
            let ios = lines.first(where: { $0.contains("MAPS_API_KEY_IOS") }),
            let value = ios.components(separatedBy: "=").last
        else {
            return
        }

        GMSServices.provideAPIKey(value)
    }
}

/// For debug purposes. Example: show onboarding on each launch.
fileprivate func resetSettings() {
    guard
        let docs = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first,
        let settings = try? FileManager.default.contentsOfDirectory(
            at: docs, includingPropertiesForKeys: nil
        ).first(where: { $0.lastPathComponent.contains("settings") })
    else {
        return
    }
    try? FileManager.default.removeItem(at: settings)
}
