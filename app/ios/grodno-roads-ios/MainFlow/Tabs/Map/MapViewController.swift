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
import BottomSheet

class MapViewController: UIViewController {
    
    private enum Constants {
        static let verticalInset: CGFloat = 12
        static let tabBarControlsHeight: CGFloat = 48
        static let tabBarHeaderHeight: CGFloat = 28
        static let bottomSheetCornerRadius: CGFloat = 28
    }
    
    private lazy var mapView: GMSMapView = {
        let options = GMSMapViewOptions()
        options.camera = GMSCameraPosition(
            latitude: 53.6813060334033,
            longitude: 23.82895337660837,
            zoom: 12
        )
        
        let mapView = GMSMapView(options: options)
        mapView.delegate = self
        mapView.isMyLocationEnabled = false
        mapView.settings.myLocationButton = false
        return mapView
    }()
    
    private let viewModel: GuidanceComponent
    
    private var cancelables: Set<AnyCancellable> = []
    private var cancellations = [Cancellation]()
    
    init(_ viewModel: GuidanceComponent) {
        self.viewModel = viewModel
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func loadView() {
        super.loadView()
        
        self.view = mapView
    }
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        
        calculateMapInsets()
    }
    
    deinit {
        cancelables.forEach { $0.cancel() }
        cancelables.removeAll()
        
        cancellations.forEach { $0.cancel() }
        cancellations.removeAll()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        asPublisher(viewModel.mapEvents)
            .map { $0.data.compactMap { MapItem(event: $0) } }
            .removeDuplicates()
            .receive(on: RunLoop.main)
            .sink { [weak self] items in
                guard let self else { return }
                updateMap(with: items)
            }.store(in: &cancelables)
        
        let mapConfig = FlowWrapper<MapConfig>(flow: viewModel.mapConfig).collect { [weak mapView] config in
            guard let config else { return }
            UIApplication.shared.isIdleTimerDisabled = config.keepScreenOn
            mapView?.isTrafficEnabled = config.trafficJanOnMap
        }
        cancellations.append(mapConfig)
    }
}

private extension MapViewController {
    func updateMap(with items: [MapItem]) {
        clearMap()
        items.forEach { item in
            add(item: item)
        }
    }
    
    func add(item: MapItem) {
        item.marker.map = mapView
    }
    
    func clearMap() {
        mapView.clear()
    }
}

private extension MapViewController {
    func calculateMapInsets() {
        let safeArea = view.safeAreaInsets
        let safeAreaBottom = safeArea.bottom == 0 ? Constants.verticalInset : 0
        let bottomPadding = safeAreaBottom + Constants.tabBarControlsHeight + Constants.tabBarHeaderHeight - Constants.verticalInset
        mapView.padding.bottom = bottomPadding
    }
}

// MARK: - GMSMapViewDelegate

extension MapViewController: GMSMapViewDelegate {
    func mapView(_ mapView: GMSMapView, didTap marker: GMSMarker) -> Bool {
        guard let mapItem = (marker as? MapMarkerProtocol)?.mapItem else {
            return false
        }
        
        let isReport = mapItem.event is MapEventReports
        let controller = isReport
                ? ReportDetailsViewController(mapItem: mapItem)
                : CameraDetailsViewController(mapItem: mapItem)
        
        presentBottomSheet(
            viewController: controller,
            configuration: BottomSheetConfiguration(
                cornerRadius: Constants.bottomSheetCornerRadius,
                pullBarConfiguration: .hidden,
                shadowConfiguration: .default
            )
        )
        
        return true
    }
}
