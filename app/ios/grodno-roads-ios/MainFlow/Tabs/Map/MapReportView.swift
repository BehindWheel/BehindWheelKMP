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
        label.textColor = .gray
        label.font = .systemFont(ofSize: 15)
        label.sizeToFit()
        return label
    }()
    
    init(text: String) {
        super.init(frame: .zero)
        
        let backgroundView = UIView()
        addSubview(backgroundView)
        
        titleLabel.text = text
        titleLabel.sizeToFit()
        
        frame.size = CGSize(
            width: titleLabel.bounds.width + 16,
            height: titleLabel.bounds.height + 24
        )
        
        backgroundView.frame = frame
        
        addSubview(titleLabel)
        titleLabel.frame.origin = CGPoint(x: 8, y: 8)

        let width = frame.width - 2
        let height = frame.height - 10
        let centerX = frame.center.x
        
        let path = UIBezierPath()
        path.move(to: CGPoint(x: 0, y: 4))
        
        path.addArc(
            withCenter: CGPoint(x: 4, y: 4),
            radius: 4,
            startAngle: CGFloat.pi,
            endAngle: 3 * CGFloat.pi / 2,
            clockwise: true
        )
        
        path.addLine(to: CGPoint(x: width - 4, y: 0))
        
        path.addArc(
            withCenter: CGPoint(x: width - 4, y: 4),
            radius: 4,
            startAngle: 3 * CGFloat.pi / 2,
            endAngle: CGFloat(0),
            clockwise: true
        )
        
        path.addLine(to: CGPoint(x: width, y: height - 4))
        
        path.addArc(
            withCenter: CGPoint(x: width - 4, y: height - 4),
            radius: 4,
            startAngle: CGFloat(0),
            endAngle: CGFloat.pi / 2,
            clockwise: true
        )
        
        path.addLine(to: CGPoint(x: centerX + 8, y: height))
        path.addLine(to: CGPoint(x: centerX, y: height + 8))
        path.addLine(to: CGPoint(x: centerX - 8, y: height))
        path.addLine(to: CGPoint(x: 4, y: height))
        
        path.addArc(
            withCenter: CGPoint(x: 4, y: height - 4),
            radius: 4,
            startAngle: CGFloat.pi / 2,
            endAngle: CGFloat.pi,
            clockwise: true
        )
        
        path.close()

        let shape = CAShapeLayer()
        shape.fillColor = UIColor.white.cgColor
        shape.strokeColor = UIColor.lightGray.cgColor
        shape.lineWidth = 1
        shape.position = CGPoint(x: 1, y: 1)
        shape.path = path.cgPath
        
        backgroundView.layer.addSublayer(shape)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
