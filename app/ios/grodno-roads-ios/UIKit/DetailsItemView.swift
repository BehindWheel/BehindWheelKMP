//
//  DetailsItemView.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 18.02.24.
//

import UIKit
import Root

class DetailsItemView: UIView {
    
    private lazy var sourceView: UIImageView = {
        let imageView = UIImageView()
        imageView.contentMode = .scaleAspectFit
        imageView.image = UIImage(systemName: "message.fill")
        imageView.translatesAutoresizingMaskIntoConstraints = false
        return imageView
    }()
    
    private lazy var textView: UILabel = {
        let label = UILabel()
        label.numberOfLines = 0
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    
    init(message: MessageItem) {
        super.init(frame: .zero)
        
        addSubview(sourceView)
        addSubview(textView)
        
        textView.text = message.message
        
        NSLayoutConstraint.activate([
            sourceView.topAnchor.constraint(equalTo: self.topAnchor),
            sourceView.leadingAnchor.constraint(equalTo: self.leadingAnchor),
            sourceView.widthAnchor.constraint(equalToConstant: 20),
            sourceView.heightAnchor.constraint(equalToConstant: 20),
            
            textView.leadingAnchor.constraint(equalTo: self.leadingAnchor, constant: 28),
            textView.trailingAnchor.constraint(equalTo: self.trailingAnchor),
            textView.topAnchor.constraint(equalTo: self.topAnchor),
            textView.bottomAnchor.constraint(equalTo: self.bottomAnchor)
        ])
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
