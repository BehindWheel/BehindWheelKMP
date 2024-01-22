//
//  CFlowWrapper.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 22.01.24.
//

import Foundation
import SwiftUI
import Root

final class CFlowWrapper<T: AnyObject>: ObservableObject {
    @Published
    private(set) var value: T?
    
    private var cancelable: Cancellation?
    
    init(_ state: CFlow<T>) {
        cancelable = FlowWrapper<T>(flow: state).collect(
            consumer: { [weak self] value in
                self?.value = value
            }
        )
    }
    
    deinit {
        self.cancelable?.cancel()
    }
}
