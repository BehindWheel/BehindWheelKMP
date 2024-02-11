//
//  MapReportView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 10.02.24.
//

import UIKit

class MapReportView: UIView {
    
    private lazy var titleLabel: UILabel = {
        let label = UILabel()
        label.textColor = .black
        label.font = .systemFont(ofSize: 13)
        label.sizeToFit()
        return label
    }()
    
    init(text: String) {
        super.init(frame: .zero)
        
        self.backgroundColor = .white
        self.layer.borderColor = UIColor.black.cgColor
        self.layer.borderWidth = 1.0
        
        titleLabel.text = text
        titleLabel.sizeToFit()
        
        self.frame.size = CGSize(
            width: titleLabel.bounds.width + 8,
            height: titleLabel.bounds.height + 8
        )
        
        addSubview(titleLabel)
        titleLabel.center = self.center
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
