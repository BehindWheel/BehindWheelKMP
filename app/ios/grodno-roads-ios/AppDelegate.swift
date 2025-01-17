//
//  AppDelegate.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 23.12.23.
//

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
        GoogleMapsInit.shared.start()

        return true
    }
}
