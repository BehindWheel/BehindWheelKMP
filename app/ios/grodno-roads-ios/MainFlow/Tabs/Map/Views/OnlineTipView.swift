//
//  OnlineTipView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 4.05.24.
//

import UIKit

class OnlineTipView: UIView {

    private lazy var titleLabel: UILabel = {
        let label = UILabel()
        label.textColor = .gray
        label.font = .systemFont(ofSize: 18, weight: .bold)
        label.text = "map_user_count_hint".localized
        label.numberOfLines = 0
        label.lineBreakMode = .byWordWrapping
        return label
    }()
    
    private lazy var closeButton: UIButton = {
        let button = UIButton()
        button.addTarget(self, action: #selector(hide), for: .touchUpInside)
        button.frame = frame
        return button
    }()
    
    private let targetPoint: CGPoint
    
    
    init(targetPoint: CGPoint) {
        self.targetPoint = targetPoint
        super.init(frame: .zero)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func didMoveToSuperview() {
        super.didMoveToSuperview()
        
        guard let superview, titleLabel.superview == nil else { return }
        
        frame = superview.frame
        
        addSubview(closeButton)
        
        let backgroundView = UIView()
        backgroundView.isUserInteractionEnabled = false
        addSubview(backgroundView)
        
        let horizontalInset: CGFloat = 16
        let labelInset: CGFloat = 8
        let tipTriangleHeight: CGFloat = 8
        
        let availableWidth = superview.frame.width - (horizontalInset * 2)
        let sizeToFit = CGSize(width: availableWidth - horizontalInset, height: CGFloat.greatestFiniteMagnitude)
        let textSize = titleLabel.sizeThatFits(sizeToFit)
        let targetHeight = textSize.height + (labelInset * 2) + tipTriangleHeight
        
        backgroundView.frame.origin = CGPoint(x: horizontalInset, y: targetPoint.y - targetHeight)
        backgroundView.frame.size = CGSize(
            width: availableWidth,
            height: targetHeight
        )
        
        let width = backgroundView.frame.width
        let height = backgroundView.frame.height - tipTriangleHeight
        let centerX = targetPoint.x - horizontalInset
        let cornerRadius: CGFloat = 8
        
        let path = UIBezierPath()
        path.move(to: CGPoint(x: 0, y: cornerRadius))
        
        path.addArc(
            withCenter: CGPoint(x: cornerRadius, y: cornerRadius),
            radius: cornerRadius,
            startAngle: CGFloat.pi,
            endAngle: 3 * CGFloat.pi / 2,
            clockwise: true
        )
        
        path.addLine(to: CGPoint(x: width - cornerRadius, y: 0))
        
        path.addArc(
            withCenter: CGPoint(x: width - cornerRadius, y: cornerRadius),
            radius: cornerRadius,
            startAngle: 3 * CGFloat.pi / 2,
            endAngle: 0,
            clockwise: true
        )
        
        path.addLine(to: CGPoint(x: width, y: height - cornerRadius))
        
        path.addArc(
            withCenter: CGPoint(x: width - cornerRadius, y: height - cornerRadius),
            radius: cornerRadius,
            startAngle: 0,
            endAngle: CGFloat.pi / 2,
            clockwise: true
        )
        
        path.addLine(to: CGPoint(x: centerX + tipTriangleHeight, y: height))
        path.addLine(to: CGPoint(x: centerX, y: height + tipTriangleHeight))
        path.addLine(to: CGPoint(x: centerX - tipTriangleHeight, y: height))
        path.addLine(to: CGPoint(x: cornerRadius, y: height))
        
        path.addArc(
            withCenter: CGPoint(x: cornerRadius, y: height - cornerRadius),
            radius: cornerRadius,
            startAngle: CGFloat.pi / 2,
            endAngle: CGFloat.pi,
            clockwise: true
        )
        
        path.close()

        let shape = CAShapeLayer()
        shape.fillColor = UIColor.white.withAlphaComponent(0.9).cgColor
        shape.shadowOpacity = 0.5
        shape.shadowOffset = .zero
        shape.shadowRadius = 1.5
        shape.path = path.cgPath
        
        backgroundView.layer.addSublayer(shape)
        
        backgroundView.addSubview(titleLabel)
        titleLabel.frame.origin = CGPoint(x: labelInset, y: labelInset)
        titleLabel.frame.size = textSize
    }
    
    @objc
    private func hide() {
        removeFromSuperview()
    }
}
