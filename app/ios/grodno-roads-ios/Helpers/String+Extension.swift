//
//  String.swift
//  grodno-roads-ios
//
//  Created by Yahor Urbanovich on 29.04.24.
//

import Foundation

extension String {
    var localized: String {
        return NSLocalizedString(self, comment: "")
    }
    
    func localized(with arguments: CVarArg...) -> String {
        String(format: NSLocalizedString(self, comment: ""), locale: .current, arguments: arguments)
    }
}
