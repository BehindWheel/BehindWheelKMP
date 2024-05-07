//
//  MapView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 20.12.23.
//

import SwiftUI
import Root

struct MapView: View {
    private let component: GuidanceComponent
    
    @StateValue
    private var specialEvents: ChildSlot<AnyObject, DialogComponent>
    
    init(_ component: GuidanceComponent) {
        self.component = component
        _specialEvents = StateValue(component.specialEventComponent.specialEvents)
    }
        
    var body: some View {
        ZStack {
            MapViewControllerBridge(component)
                .ignoresSafeArea()
        }.alert(
            item: specialEvents.child?.instance,
            onDismiss: { $0.dismiss() },
            title: { _ in Text("") },
            message: { event in
                var text: String {
                    switch event.eventType {
                    case .spring: "event_reminder_spring_body".localized
                    case .autumn: "event_reminder_autumn_body".localized
                    default: fatalError("unsupported event type")
                    }
                }
                return Text(text)
            },
            actions: { _ in Button("OK", action: {}) }
        )
    }
}
