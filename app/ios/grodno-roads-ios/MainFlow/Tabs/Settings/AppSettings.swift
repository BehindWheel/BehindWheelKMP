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
                Text("Настройки")
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
                    SettingsHeaderView(title: "Main")
                        .frame(height: 40)
                        .padding([.leading, .trailing], 16)
                        .padding(.top, 8)
                    
                    SettingsItemView(
                        leadingIcon: "swatchpalette.fill",
                        headlineText: "Appearance",
                        action: { component.open(page: .appearance) }
                    )
                    .frame(height: 40)
                    .padding([.leading, .trailing], 16)
                    
                    SettingsItemView(
                        leadingIcon: "map.fill",
                        headlineText: "Map",
                        action: { component.open(page: .mapsettings) }
                    )
                    .frame(height: 40)
                    .padding([.leading, .trailing], 16)
                    
                    SettingsItemView(
                        leadingIcon: "speaker.wave.2.bubble.fill",
                        headlineText: "Alerts",
                        action: { component.open(page: .alerts) }
                    )
                    .frame(height: 40)
                    .padding([.leading, .trailing], 16)
                    .padding(.bottom, 8)
                }
                .background(.white)
                .clipShape(.rect(cornerRadius: 28))
                
                VStack(alignment: .leading, spacing: 0) {
                    SettingsHeaderView(title: "Other")
                        .frame(height: 40)
                        .padding([.leading, .trailing], 16)
                        .padding(.top, 8)
                    
                    SettingsItemView(
                        leadingIcon: "newspaper.fill",
                        headlineText: "Changelog",
                        action: { component.open(page: .changelog) }
                    )
                    .frame(height: 40)
                    .padding([.leading, .trailing], 16)
                    
                    SettingsItemView(
                        leadingIcon: "quote.bubble.fill",
                        headlineText: "FAQ",
                        action: { component.open(page: .faq) }
                    )
                    .frame(height: 40)
                    .padding([.leading, .trailing], 16)
                    .padding(.bottom, 8)
                }
                .background(.white)
                .clipShape(.rect(cornerRadius: 28))
                .padding(.top, 8)
                
                HStack {
                    Spacer()
                    SettingsRoundItem("Chat", image: "message.fill") {
                        openUrl("https://t.me/grodnoroads_chat")
                    }
                    Spacer()
                    SettingsRoundItem("Channel", image: "paperplane.fill") {
                        openUrl("https://t.me/grodno_roads")
                    }
                    Spacer()
                    SettingsRoundItem("Share", image: "square.and.arrow.up.fill", action: { shareAppLink() })
                    Spacer()
                }
                .padding(.top, 18)
                
                
                Text("Version: \(component.appVersion)")
                    .font(.system(size: 12, weight: .medium))
                    .frame(maxWidth: .infinity, alignment: .center)
                    .padding()
                    .padding(.top, 12)
                
                HStack(alignment: .center) {
                    Spacer()
                    Button {
                        openUrl("https://github.com/grodnoroads/GrodnoRoads/blob/release/TermsConditions.md")
                    } label: {
                        Text("Условия использования").font(.system(size: 10, weight: .medium))
                    }
                    .tint(.black)
                    Spacer()
                    Text("•") .font(.system(size: 10, weight: .medium))
                    Spacer()
                    Button {
                        openUrl("https://github.com/grodnoroads/GrodnoRoads/blob/release/PrivacyPolicy.md")
                    } label: {
                        Text("Политика конфидециальности")
                            .font(.system(size: 10, weight: .medium))
                    }
                    .tint(.black)
                    Spacer()
                }
                .padding(.top, 12)
            }
            .padding(.horizontal)
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
            let url = URL(string: "https://t.me/grodno_roads")
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
