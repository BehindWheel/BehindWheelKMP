//
//  UIView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 11.02.24.
//

import UIKit

extension UIView {
    var snapshot: UIImage {
        let renderer = UIGraphicsImageRenderer(size: self.bounds.size)
        let image = renderer.image { ctx in
            self.drawHierarchy(in: self.bounds, afterScreenUpdates: true)
        }
        return image
    }
}
