//
//  CFlowWrapper.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 22.01.24.
//

import Combine
import SwiftUI
import Root

func asPublisher<T>(_ flow: CFlow<T>) -> AnyPublisher<T, Never> {
    return Deferred<Publishers.HandleEvents<PassthroughSubject<T, Never>>> {
        let subject = PassthroughSubject<T, Never>()
        let closable = FlowWrapper<T>(flow: flow).collect { next in
            guard let next else { return }
            subject.send(next)
        }
        return subject.handleEvents(receiveCancel: {
            closable.cancel()
        })
    }.eraseToAnyPublisher()
}

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

@propertyWrapper struct CFlowValue<T: AnyObject>: DynamicProperty {
    @ObservedObject
    private(set) var obj: CFlowWrapper<T>

    var wrappedValue: T? { obj.value }

    init(_ value: CFlow<T>) {
        obj = CFlowWrapper(value)
    }
}
