import SwiftUI
import Root

@propertyWrapper struct StateValue<T : AnyObject>: DynamicProperty {
    @ObservedObject
    private var obj: ObservableState<T>

    var wrappedValue: T { obj.value }

    init(_ value: CStateFlow<T>) {
        obj = ObservableState(value)
    }
}
