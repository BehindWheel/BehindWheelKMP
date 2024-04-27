//
//  StringResource.swift
//  grodno-roads-ios
//
//  Created by Yahor Urbanovich on 27.04.24.
//

import Root

extension Root.StringResource {
    func localize() -> String {
        self.desc().localized()
    }
}
