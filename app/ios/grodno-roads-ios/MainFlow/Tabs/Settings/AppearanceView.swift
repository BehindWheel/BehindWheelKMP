//
//  AppearanceView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 18.12.23.
//

import SwiftUI
import Root

struct AppearanceView: View {
    private let component: AppearanceComponent
    private let onBack: (() -> Void)
    
    private let appLanguageCode: String = {
        let code = Bundle.main.preferredLocalizations.first
        switch code {
        case "ru":
            return "appearance_app_language_ru".localized
        case "be":
            return "appearance_app_language_be".localized
        case "en":
            return "appearance_app_language_en".localized
        default:
            return ""
        }
    }()
    
    @StateValue
    private var state: AppearanceStoreState
    
    init(_ component: AppearanceComponent, onBack: @escaping (() -> Void)) {
        self.component = component
        self.onBack = onBack
        
        _state = StateValue(component.state)
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            Button {
                UIApplication.shared.open(
                    URL(string: UIApplication.openSettingsURLString)!
                )
            } label: {
                HStack {
                    Image(systemName: "globe")
                    Text("appearance_app_language".localized)
                        .font(.system(size: 15))
                        .foregroundStyle(.black.opacity(0.75))
                    Spacer()
                    Text(appLanguageCode)
                        .font(.system(size: 16, weight: .medium))
                        .foregroundStyle(.black.opacity(0.75))
                    Image(systemName: "chevron.right")
                        .tint(.black)
                }
            }
            .tint(.gray)
            .padding([.top, .horizontal], 16)
#if DEBUG
            Text("settings_category_other".localized)
                .font(.system(size: 16, weight: .medium))
                .foregroundStyle(.black.opacity(0.75))
                .padding([.top, .horizontal], 16)
            HStack(alignment: .top) {
                Image(systemName: "sun.max")
                    .foregroundStyle(.gray)
                VStack(alignment: .leading, spacing: 4) {
                    Text("appearance_keep_screen_on".localized)
                        .font(.system(size: 16, weight: .medium))
                        .foregroundStyle(.black.opacity(0.75))
                    Text("appearance_keep_screen_on_description".localized)
                        .font(.system(size: 15))
                        .foregroundStyle(.black.opacity(0.75))
                }
                .padding(.horizontal, 8)
                Spacer()
                
                let keepScreenOnBinding = Binding<Bool>(
                    get: { state.appearanceState.keepScreenOn.enabled},
                    set: { component.update(preference: AppearanceComponentAppearancePrefKeepScreenOn(enabled: $0))}
                )
                
                Toggle("", isOn: keepScreenOnBinding)
                    .labelsHidden()
                    .onChange(of: keepScreenOnBinding.wrappedValue) { newValue in
                        keepScreenOnBinding.wrappedValue = newValue
                    }
            }
            .padding(.horizontal, 16)
            .padding(.top, 8)
#endif
            Spacer()
        }
        .navigation(
            title: "settings_section_appearance".localized,
            onBack: onBack
        )
    }
}

#Preview {
    AppearanceView(AppearanceComponentPreview()) {}
}
