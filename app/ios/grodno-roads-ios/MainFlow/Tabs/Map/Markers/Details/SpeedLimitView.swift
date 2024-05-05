//
//  SpeedLimitView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 20.04.24.
//

import UIKit
import Root

class SpeedLimitView: UIView {
    
    var isCar: Bool = true {
        didSet {
            vehicleImageView.image = isCar ? MR.images().ic_car.asUIImage().withRenderingMode(.alwaysTemplate) : MR.images().ic_truck.asUIImage().withRenderingMode(.alwaysTemplate)
        }
    }
    
    var speedLimit: String = "" {
        didSet {
            limitLabel.text = speedLimit
        }
    }
    
    private lazy var contentView: UIView = {
        let view = UIView()
        view.translatesAutoresizingMaskIntoConstraints = false
        return view
    }()
    
    private lazy var vehicleImageView: UIImageView = {
        let imageView = UIImageView()
        imageView.contentMode = .scaleAspectFit
        imageView.tintColor = .black
        imageView.translatesAutoresizingMaskIntoConstraints = false
        return imageView
    }()
    
    private lazy var limitLabel: UILabel = {
        let label = UILabel()
        label.textAlignment = .center
        label.font = .systemFont(ofSize: 16, weight: .bold)
        label.layer.cornerRadius = 22
        label.layer.borderWidth = 3
        label.layer.borderColor = UIColor.red.cgColor
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        addSubview(contentView)
        contentView.addSubview(vehicleImageView)
        contentView.addSubview(limitLabel)
        
        NSLayoutConstraint.activate([
            contentView.leadingAnchor.constraint(greaterThanOrEqualTo: leadingAnchor),
            contentView.trailingAnchor.constraint(lessThanOrEqualTo: trailingAnchor),
            
            contentView.centerXAnchor.constraint(equalTo: centerXAnchor),
            contentView.topAnchor.constraint(equalTo: topAnchor),
            contentView.bottomAnchor.constraint(equalTo: bottomAnchor),
            contentView.heightAnchor.constraint(equalToConstant: 44),
            
            
            vehicleImageView.leadingAnchor.constraint(equalTo: contentView.leadingAnchor),
            vehicleImageView.centerYAnchor.constraint(equalTo: contentView.centerYAnchor),
            vehicleImageView.heightAnchor.constraint(equalToConstant: 24),
            vehicleImageView.widthAnchor.constraint(equalToConstant: 24),
            
            limitLabel.leadingAnchor.constraint(
                equalTo: vehicleImageView.trailingAnchor, constant: 18
            ),
            limitLabel.topAnchor.constraint(equalTo: contentView.topAnchor),
            limitLabel.bottomAnchor.constraint(equalTo: contentView.bottomAnchor),
            limitLabel.trailingAnchor.constraint(equalTo: contentView.trailingAnchor),
            limitLabel.widthAnchor.constraint(equalToConstant: 44)
        ])
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
