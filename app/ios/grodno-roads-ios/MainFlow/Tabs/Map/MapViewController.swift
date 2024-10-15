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
        static let horizontalInset: CGFloat = 16
        static let onlineButtonBottomInset: CGFloat = 8
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
    
    private lazy var onlineButtonAttributes: AttributeContainer = {
        AttributeContainer([
            .font: UIFont.systemFont(ofSize: 13, weight: .medium),
            .foregroundColor: UIColor.black,
            .paragraphStyle: {
                let p = NSMutableParagraphStyle()
                p.minimumLineHeight = 14
                p.maximumLineHeight = 14
                return p
            }()
        ])
    }()
    
    private lazy var onlineButton: UIButton = {
        var config = UIButton.Configuration.filled()
        config.image = UIImage(
            systemName: "info.circle.fill",
            withConfiguration: UIImage.SymbolConfiguration(pointSize: 15, weight: .regular, scale: .small)
        )
        config.imagePadding = 2
        config.contentInsets = NSDirectionalEdgeInsets(top: 1, leading: 1, bottom: 1, trailing: 3)
        config.cornerStyle = .capsule
        config.baseBackgroundColor = UIColor.white
        config.baseForegroundColor = UIColor.systemBlue
        
        let button = UIButton(
            configuration: config,
            primaryAction: UIAction(handler: { [weak self] _ in
                self?.showOnlineTipView()
            })
        )
        button.translatesAutoresizingMaskIntoConstraints = false
        button.layer.shadowOpacity = 0.5
        button.layer.shadowOffset = .zero
        button.layer.shadowRadius = 1.5
        button.isHidden = true
        
        return button
    }()
    
    private var onlineButtonBottomConstraint: NSLayoutConstraint?
    
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
        
        setupViews()
        setupBinding()
    }
}

// MARK: - Controller setup

private extension MapViewController {
    func setupViews() {
        view.addSubview(onlineButton)
        onlineButton.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -Constants.horizontalInset).isActive = true
        onlineButtonBottomConstraint = onlineButton.bottomAnchor.constraint(equalTo: view.bottomAnchor)
        onlineButtonBottomConstraint?.isActive = true
    }
    
    func setupBinding() {
        asPublisher(viewModel.mapEvents)
            .map { $0.data.compactMap { MapItem(event: $0) } }
            .removeDuplicates()
            .receive(on: RunLoop.main)
            .sink { [weak self] items in
                guard let self else { return }
                updateMap(with: items)
            }.store(in: &cancelables)
        
        let userCount = FlowWrapper<KotlinInt>(flow: viewModel.userCount).collect { [weak self] count in
            guard let self, let count else { return }
            if onlineButton.isHidden { onlineButton.isHidden = false }
            onlineButton.configuration?.attributedTitle = AttributedString(
                "map_user_count".localized(with: count.intValue), attributes: onlineButtonAttributes
            )
        }
        cancellations.append(userCount)
        
        let mapConfig = FlowWrapper<MapConfig>(flow: viewModel.mapConfig).collect { [weak mapView] config in
            guard let config else { return }
            UIApplication.shared.isIdleTimerDisabled = config.keepScreenOn
            mapView?.isTrafficEnabled = config.trafficJanOnMap
        }
        cancellations.append(mapConfig)
    }
}

// MARK: - Controller actions

private extension MapViewController {
    func showOnlineTipView() {
        view.addSubview(
            OnlineTipView(targetPoint: CGPoint(x: onlineButton.center.x, y: onlineButton.frame.minY - 3))
        )
    }
}

// MARK: - Map action

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

    func calculateMapInsets() {
        let safeArea = view.safeAreaInsets
        let safeAreaBottom = safeArea.bottom == 0 ? Constants.verticalInset : 0
        let bottomPadding = safeAreaBottom + Constants.tabBarControlsHeight + Constants.tabBarHeaderHeight - Constants.verticalInset
        mapView.padding.bottom = bottomPadding
        onlineButtonBottomConstraint?.constant = -bottomPadding - safeArea.bottom - Constants.onlineButtonBottomInset
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
