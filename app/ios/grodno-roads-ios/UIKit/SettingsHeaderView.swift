//
//  SettingsHeaderView.swift
//  grodno-roads-ios
//
//  Created by Yahor Urbanovich on 24.12.23.
//

import SwiftUI

struct SettingsHeaderView : View {
    var title: String
    
    var body: some View {
       Text(title)
            .font(.system(size: 16, weight: .medium))
            .foregroundColor(.black)
    }
}

#Preview {
    SettingsHeaderView(title: "Main")
}
