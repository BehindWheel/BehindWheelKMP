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
    
    private lazy var mapView = GMSMapView()
    
    private let viewModel: GuidanceComponent
    
    private var cancelables: Set<AnyCancellable> = []
    
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
        self.mapView.delegate = self
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        mapView.moveCamera(
            GMSCameraUpdate.setCamera(
                GMSCameraPosition(
                    latitude: 53.6813060334033,
                    longitude: 23.82895337660837,
                    zoom: 12
                )
            )
        )
        
        asPublisher(viewModel.mapEvents)
            .map { $0.data.compactMap { MapItem(event: $0) } }
            .removeDuplicates()
            .receive(on: RunLoop.main)
            .sink { [weak self] items in
                guard let self else { return }
                updateMap(with: items)
            }.store(in: &cancelables)
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
                cornerRadius: 28,
                pullBarConfiguration: .hidden,
                shadowConfiguration: .default
            )
        )
        
        return true
    }
}
