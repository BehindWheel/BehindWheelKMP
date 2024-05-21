//
//  Image.swift
//  grodno-roads-ios
//
//  Created by Yahor Urbanovich on 27.04.24.
//

import SwiftUI
import Root

extension Root.ImageResource {
    func asUIImage() -> UIImage {
        self.toUIImage() ?? UIImage()
    }
}
