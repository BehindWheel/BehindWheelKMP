//
//  AppSettings.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 20.12.23.
//

import SwiftUI
import Root

struct AppSettings: View {
    private let component: AppSettingsComponent
    
    init(_ component: AppSettingsComponent) {
        self.component = component
    }
    
    var body: some View {
        GeometryReader { geometry in
            VStack(alignment: .leading, spacing: 0) {
                Text("tab_settings".localized)
                    .font(.system(size: 24))
                    .frame(height: 32)
                    .frame(maxWidth: .infinity,alignment: .center)
                    .padding(.top, 10)
                    .padding(.bottom, {
                        let topSafeArea = geometry.safeAreaInsets.top
                        let padding: CGFloat = topSafeArea > 20 ? 24 : 16
                        return padding
                    }())
                
                VStack(alignment: .leading, spacing: 0) {
                    SettingsHeaderView(title: "settings_category_main".localized)
                        .frame(height: 40)
                        .padding([.leading, .trailing], 16)
                        .padding(.top, 8)
                    
                    SettingsItemView(
                        leadingIcon: MR.images().ic_appearance.asUIImage(),
                        headlineText: "settings_section_appearance".localized,
                        action: { component.open(page: .appearance) }
                    )
                    .frame(height: 40)
                    .padding([.leading, .trailing], 16)
                    
                    SettingsItemView(
                        leadingIcon: MR.images().ic_map.asUIImage(),
                        headlineText: "settings_section_map".localized,
                        action: { component.open(page: .mapsettings) }
                    )
                    .frame(height: 40)
                    .padding([.leading, .trailing], 16)
                    .modify { view in
#if DEBUG
                        return view
#else
                        return view.padding(.bottom, 8)
#endif
                    }
#if DEBUG
                    SettingsItemView(
                        leadingIcon: MR.images().ic_notification_badge.asUIImage(),
                        headlineText: "settings_section_alerts".localized,
                        action: { component.open(page: .alerts) }
                    )
                    .frame(height: 40)
                    .padding([.leading, .trailing], 16)
                    .padding(.bottom, 8)
#endif
                }
                .background(.white)
                .clipShape(.rect(cornerRadius: 28))
                
                VStack(alignment: .leading, spacing: 0) {
                    SettingsHeaderView(title: "settings_category_other".localized)
                        .frame(height: 40)
                        .padding([.leading, .trailing], 16)
                        .padding(.top, 8)
                    
                    SettingsItemView(
                        leadingIcon: MR.images().ic_changelog.asUIImage(),
                        headlineText: "settings_section_changelog".localized,
                        action: { component.open(page: .changelog) }
                    )
                    .frame(height: 40)
                    .padding([.leading, .trailing], 16)
                    
                    SettingsItemView(
                        leadingIcon: MR.images().ic_faq.asUIImage(),
                        headlineText: "settings_section_faq".localized,
                        action: { component.open(page: .faq) }
                    )
                    .frame(height: 40)
                    .padding([.leading, .trailing], 16)
                    .padding(.bottom, 8)
                }
                .background(.white)
                .clipShape(.rect(cornerRadius: 28))
                .padding(.top, 8)
                
                HStack(alignment: .top, spacing: 8) {
                    SettingsRoundItem("social_telegram_chat".localized,
                                      image: MR.images().ic_chat.asUIImage()) {
                        openUrl(Constants.shared.TG_CHAT_LINK)
                    }
                    .frame(width: (geometry.size.width - 48) / 3)
                    SettingsRoundItem("social_telegram_channel".localized, 
                                      image: MR.images().ic_telegram.asUIImage()) {
                        openUrl(Constants.shared.TG_CHANNEL_LINK)
                    }
                    .frame(width: (geometry.size.width - 48) / 3)
                    SettingsRoundItem("social_share_app".localized, 
                                      image: MR.images().ic_share.asUIImage(),
                                      action: { shareAppLink()})
                    .frame(width: (geometry.size.width - 48) / 3)
                }
                .padding(.top, 18)
                
                
                Text("app_version".localized(with: component.appVersion))
                    .font(.system(size: 12, weight: .medium))
                    .frame(maxWidth: .infinity, alignment: .center)
                    .padding()
                    .padding(.top, 12)
                
                HStack(alignment: .center) {
                    Spacer()
                    Button {
                        openUrl(Constants.shared.TERMS_OF_SERVICE_LINK)
                    } label: {
                        Text("terms_of_service".localized).font(.system(size: 10, weight: .medium))
                    }
                    .tint(.black)
                    Text(Constants.shared.BULLET_SEPARATOR)
                        .font(.system(size: 10, weight: .medium))
                    Button {
                        openUrl(Constants.shared.PRIVACY_POLICY_LINK)
                    } label: {
                        Text("privacy_policy".localized)
                            .font(.system(size: 10, weight: .medium))
                    }
                    .tint(.black)
                    Spacer()
                }
                .padding(.top, 12)
            }
            .padding(.horizontal, 16)
            .padding(.bottom, {
                let bottomSafeArea = geometry.safeAreaInsets.bottom
                let height: CGFloat = bottomSafeArea == 0 ? 16 : bottomSafeArea - 12
                return height + 76
            }())
            .scrollable()
            .background(.regularMaterial)
        }
    }
    
    private func shareAppLink() {
        guard
            let vc = UIApplication.shared.connectedScenes
                .compactMap({$0 as? UIWindowScene}).first?
                .windows.first?.rootViewController,
            let url = URL(string: Constants.shared.APPSTORE_STORE_LINK)
        else {
            return
        }
        
        let shareActivity = UIActivityViewController(activityItems: [url], applicationActivities: nil)
        shareActivity.popoverPresentationController?.sourceView = vc.view
        shareActivity.popoverPresentationController?.sourceRect = CGRect(x: UIScreen.main.bounds.width / 2, y: UIScreen.main.bounds.height, width: 0, height: 0)
        shareActivity.popoverPresentationController?.permittedArrowDirections = UIPopoverArrowDirection.down
        vc.present(shareActivity, animated: true, completion: nil)
    }
    
    private func openUrl(_ url: String) {
        guard let url = URL(string: url) else { return }
        UIApplication.shared.open(url)
    }
}

#Preview {
    AppSettings(AppSettingsComponentPreview())
}
