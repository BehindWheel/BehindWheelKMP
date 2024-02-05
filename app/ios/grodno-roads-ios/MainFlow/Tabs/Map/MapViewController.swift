//
//  MapViewController.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 25.12.23.
//

import Combine
import GoogleMaps
import UIKit
import Root

class MapViewController: UIViewController {
    
    private lazy var map = GMSMapView()
    
    private let viewModel: GuidanceComponent
    
    private var cancelables: Set<AnyCancellable> = []
    
    init(_ viewModel: GuidanceComponent) {
        self.viewModel = viewModel
        super.init(nibName: nil, bundle: nil)
    }
    
    deinit {
        cancelables.forEach { $0.cancel() }
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func loadView() {
        super.loadView()
        self.view = map
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        CFlowWrapper(viewModel.mapEvents).$value.sink { events in
            guard let events else {
                print("events == nil")
                return
            }
            print(events)
        }.store(in: &cancelables)

        CFlowWrapper(viewModel.userCount).$value.sink { count in
            guard let count else { return }
            print("Online: \(count)")
        }.store(in: &cancelables)
    }
}
