import SwiftUI
import UIKit
import Root

/*
 Source: https://gist.github.com/AJIEKCX/e1c50fb3b1c0496138bcdbb2779e3898
 */

/*
 A custom implementation of the stack view wrapper with:
 - no indices for `onBack` action
 - no `getTitle`
 - no `NavigationStack`
 - custom UINavigationController
 
  https://github.com/arkivanov/Decompose/blob/11a66f0ec46e42259af5510fc5bb28250e027924/sample/app-ios/app-ios/DecomposeHelpers/StackView.swift
*/

struct StackView<T: AnyObject, Content: View>: View {
    @ObservedObject
    var stackValue: ObservableState<ChildStack<AnyObject, T>>
        
    @ViewBuilder
    var childContent: (T) -> Content
    
    var onBack: () -> Void
    
    var stack: [Child<AnyObject, T>] { stackValue.value.items }
    
    var body: some View {
        StackInteropView(
            components: stack.map { $0.instance! },
            onBack: onBack,
            childContent: childContent
        )
        .ignoresSafeArea(.container)
    }
}

private struct StackInteropView<T: AnyObject, Content: View>: UIViewControllerRepresentable {
    var components: [T]
    var onBack: () -> Void
    var childContent: (T) -> Content
    
    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }
    
    func makeUIViewController(context: Context) -> UINavigationController {
        context.coordinator.syncChanges(self)
        let navigationController = CustomNavigationController(
            rootViewController: context.coordinator.viewControllers.first!
        )
        
        return navigationController
    }
    
    func updateUIViewController(
        _ navigationController: UINavigationController,
        context: Context
    ) {
        context.coordinator.syncChanges(self)
        navigationController.setViewControllers(
            context.coordinator.viewControllers,
            animated: true
        )
    }
    
    private func createViewController(
        _ component: T,
        _ coordinator: Coordinator
    ) -> NavigationItemHostingController {
        let controller = NavigationItemHostingController(
            rootView: childContent(component)
        )
        controller.coordinator = coordinator
        controller.component = component
        controller.onBack = onBack
        
        return controller
    }
    
    private final class CustomNavigationController: UINavigationController, UIGestureRecognizerDelegate {
        override func viewDidLoad() {
            super.viewDidLoad()
            navigationBar.isHidden = true
            interactivePopGestureRecognizer?.delegate = self
        }
        
        // fixes swipes back, when parent stack view intercepts child's gestures
        func gestureRecognizerShouldBegin(_ gestureRecognizer: UIGestureRecognizer) -> Bool {
            viewControllers.count > 1
        }
    }
    
    fileprivate final class Coordinator: NSObject {
        var parent: StackInteropView<T, Content>
        var viewControllers = [NavigationItemHostingController]()
        var preservedComponents = [T]()
        
        init(_ parent: StackInteropView<T, Content>) {
            self.parent = parent
        }
        
        func syncChanges(_ parent: StackInteropView<T, Content>) {
            self.parent = parent
            let count = max(preservedComponents.count, parent.components.count)
            
            for i in 0..<count {
                if (i >= parent.components.count) {
                    viewControllers.removeLast()
                } else if (i >= preservedComponents.count) {
                    viewControllers.append(parent.createViewController(parent.components[i], self))
                } else if (parent.components[i] !== preservedComponents[i]) {
                    viewControllers[i] = parent.createViewController(parent.components[i], self)
                }
            }
            
            preservedComponents = parent.components
        }
    }
    
    fileprivate final class NavigationItemHostingController: UIHostingController<Content> {
        fileprivate(set) weak var coordinator: Coordinator?
        fileprivate(set) var component: T?
        fileprivate(set) var onBack: (() -> Void)?
        
        override func viewDidDisappear(_ animated: Bool) {
            super.viewDidDisappear(animated)
            
            if isMovingFromParent && coordinator?.preservedComponents.last === component {
                onBack?()
            }
        }
    }
}
