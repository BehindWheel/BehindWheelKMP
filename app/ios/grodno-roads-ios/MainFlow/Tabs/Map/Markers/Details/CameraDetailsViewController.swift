//
//  CameraDetailsViewController.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 20.04.24.
//

import UIKit
import Root

class CameraDetailsViewController: UIViewController {

    private enum Constants {
        static let rootEdge: CGFloat = 20
        static let rootTop: CGFloat = 28
        static let grabberWidth: CGFloat = 32
        static let grabberHeight: CGFloat = 4
        static let grabberBottomInset: CGFloat = 14
    }

    private var isConstraintCreationNeeded: Bool = true
    
    private var bottomInset: CGFloat = Constants.rootEdge
    
    private weak var mapItem: MapItem?
    
    private lazy var grabberView: UIView = {
        let view = UIView()
        view.backgroundColor = .lightGray
        view.translatesAutoresizingMaskIntoConstraints = false
        return view
    }()
    
    private lazy var cameraImageView: UIImageView = {
        let image = UIImageView()
        image.translatesAutoresizingMaskIntoConstraints = false
        image.contentMode = .scaleAspectFit
        return image
    }()
    
    private lazy var titleLabel: UILabel = {
        let label = UILabel()
        label.font = .systemFont(ofSize: 16, weight: .bold)
        label.translatesAutoresizingMaskIntoConstraints = false
        label.numberOfLines = 0
        return label
    }()
    
    private lazy var typeLabel: UILabel = {
        let label = UILabel()
        label.font = .systemFont(ofSize: 14, weight: .medium)
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    
    private lazy var updatedLabel: UILabel = {
        let label = UILabel()
        label.font = .systemFont(ofSize: 12, weight: .regular)
        label.translatesAutoresizingMaskIntoConstraints = false
        label.textColor = .lightGray
        return label
    }()
    
    private lazy var descriptionStackView: UIStackView = {
        let stack = UIStackView()
        stack.translatesAutoresizingMaskIntoConstraints = false
        stack.axis = .vertical
        stack.spacing = 8
        return stack
    }()
    
    private lazy var limitsStackView: UIStackView = {
        let stack = UIStackView()
        stack.translatesAutoresizingMaskIntoConstraints = false
        stack.spacing = 8
        stack.distribution = .fillEqually
        stack.alignment = .center
        return stack
    }()

    init(mapItem: MapItem) {
        self.mapItem = mapItem
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override var preferredContentSize: CGSize {
        get {
            return CGSize(
                width: UIScreen.main.bounds.width,
                height: descriptionStackView.frame.height +
                        limitsStackView.frame.height +
                        bottomInset + Constants.grabberBottomInset +
                        Constants.rootTop + 16
            )
        } set {
            self.preferredContentSize = newValue
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        view.backgroundColor = .white
        
        guard let mapItem else {
            return
        }
        
        setupCamera(mapItem: mapItem)
    }
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        
        guard isConstraintCreationNeeded else {
            return
        }
        isConstraintCreationNeeded = false
        
        view.addSubview(grabberView)
        view.addSubview(cameraImageView)
        view.addSubview(descriptionStackView)
        view.addSubview(limitsStackView)
        
        descriptionStackView.addArrangedSubview(titleLabel)
        descriptionStackView.addArrangedSubview(typeLabel)
        descriptionStackView.addArrangedSubview(updatedLabel)
        
        let safeArea = view.window?.safeAreaInsets ?? .zero
        bottomInset = safeArea.bottom > Constants.rootEdge
                        ? 0
                        : min(Constants.rootEdge, abs(safeArea.bottom - Constants.rootEdge))
        
        NSLayoutConstraint.activate(
            [
                grabberView.widthAnchor.constraint(equalToConstant: Constants.grabberWidth),
                grabberView.heightAnchor.constraint(equalToConstant: Constants.grabberHeight),
                grabberView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
                grabberView.bottomAnchor.constraint(
                    equalTo: cameraImageView.topAnchor, constant: -Constants.grabberBottomInset
                ),
                
                cameraImageView.heightAnchor.constraint(equalToConstant: 64),
                cameraImageView.widthAnchor.constraint(equalToConstant: 64),
                cameraImageView.leadingAnchor.constraint(
                    equalTo: view.leadingAnchor, constant: Constants.rootEdge
                ),
                
                descriptionStackView.topAnchor.constraint(equalTo: cameraImageView.topAnchor),
                descriptionStackView.leadingAnchor.constraint(
                    equalTo: cameraImageView.trailingAnchor, constant: 16
                ),
                
                descriptionStackView.trailingAnchor.constraint(
                    equalTo: view.trailingAnchor, constant: -Constants.rootEdge
                ),
                
                limitsStackView.topAnchor.constraint(equalTo: descriptionStackView.bottomAnchor, constant: 16),
                limitsStackView.leadingAnchor.constraint(
                    equalTo: view.leadingAnchor, constant: Constants.rootEdge
                ),
                limitsStackView.trailingAnchor.constraint(
                    equalTo: view.trailingAnchor, constant: -Constants.rootEdge
                ),
                limitsStackView.bottomAnchor.constraint(
                    equalTo: view.safeAreaLayoutGuide.bottomAnchor, constant: -bottomInset
                ),
            ]
        )
    }
    
    private func setupCamera(mapItem: MapItem) {
        guard let camera = mapItem.event as? MapEventCamera else {
            return
        }
        
        switch camera.cameraType {
        case .stationarycamera:
            cameraImageView.image = MR.images().nt_ic_stationary_camera_bold.asUIImage()
        case .mobilecamera:
            cameraImageView.image = MR.images().nt_ic_mobile_camera_bold.asUIImage()
        case .mediumspeedcamera:
            cameraImageView.image = MR.images().nt_ic_medium_speed_camera_bold.asUIImage()
        default: fatalError("unsupported message type")
        }
                
        titleLabel.text = camera.name
        typeLabel.text = camera.nativeCameraType.title
        
        let formatter = Foundation.DateFormatter()
        formatter.locale = Locale(identifier: "en_US_POSIX")
        formatter.timeZone = TimeZone(abbreviation: "GMT+3:00")
        formatter.dateFormat = "dd.MM.yyy"
        
        let time = TimeInterval(camera.updateTime / 1000)
        let date = Date(timeIntervalSince1970: time)
        let dateStr = formatter.string(from: date)
        updatedLabel.text = "Обновлено: \(dateStr)"
        
        let carLimit = SpeedLimitView()
        carLimit.speedLimit = camera.speedCar.description
        carLimit.isCar = true
        limitsStackView.addArrangedSubview(carLimit)
        
        let truckLimit = SpeedLimitView()
        truckLimit.isCar = false
        truckLimit.speedLimit = camera.speedTruck.description
        limitsStackView.addArrangedSubview(truckLimit)
    }
}
