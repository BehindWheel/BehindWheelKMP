//
//  ReportDetailsViewController.swift
//  grodno-roads-ios
//
//  Created by Vladislav Sitsko on 13.02.24.
//

import UIKit
import Root
import BottomSheet

class ReportDetailsViewController: UIViewController {
    
    private enum Constants {
        static let rootEdge: CGFloat = 20
        static let rootTop: CGFloat = 28
        static let grabberWidth: CGFloat = 32
        static let grabberHeight: CGFloat = 4
        static let grabberBottomInset: CGFloat = 14
    }

    private var isConstraintCreationNeeded: Bool = true
    
    private var bottomInset: CGFloat = Constants.rootEdge
    
    private weak var mapItem: MapItem?
    
    private lazy var grabberView: UIView = {
        let view = UIView()
        view.backgroundColor = .lightGray
        view.translatesAutoresizingMaskIntoConstraints = false
        return view
    }()
    
    private lazy var titleLabel: UILabel = {
        let label = UILabel()
        label.font = .systemFont(ofSize: 24, weight: .medium)
        label.textAlignment = .center
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    
    private lazy var _scrollView: UIScrollView = {
        let scroll = UIScrollView()
        scroll.translatesAutoresizingMaskIntoConstraints = false
        scroll.alwaysBounceVertical = false
        scroll.showsVerticalScrollIndicator = false
        return scroll
    }()
    
    private lazy var stackView: UIStackView = {
        let stack = UIStackView()
        stack.translatesAutoresizingMaskIntoConstraints = false
        stack.axis = .vertical
        stack.spacing = 16
        return stack
    }()
    
    private lazy var separatorView: UIView = {
        let view = UIView()
        view.backgroundColor = .lightGray
        view.translatesAutoresizingMaskIntoConstraints = false
        return view
    }()
    
    private lazy var doneButton: UIButton = {
        let view = UIButton(type: .system)
        view.translatesAutoresizingMaskIntoConstraints = false
        view.addAction(UIAction { [weak self] _ in
            self?.dismiss(animated: true)
        }, for: .touchUpInside)
        view.setTitle("OK", for: .normal)
        view.titleLabel?.font = UIFont.systemFont(ofSize: 16, weight: .bold)
        view.tintColor = UIColor.black
        return view
    }()

    init(mapItem: MapItem) {
        self.mapItem = mapItem
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override var preferredContentSize: CGSize {
        get {
            CGSize(
                width: UIScreen.main.bounds.width,
                height: _scrollView.contentSize.height +
                        bottomInset + Constants.grabberBottomInset +
                        titleLabel.frame.height + Constants.rootTop +
                        Constants.grabberBottomInset + separatorView.frame.height +
                        doneButton.frame.height + 8
            )
        } set {
            self.preferredContentSize = newValue
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        view.backgroundColor = .white
        
        if let camera = mapItem?.event as? MapEventCamera {
            setupCamera(camera: camera)
        } else if let reports = mapItem?.event as? MapEventReports {
            setupReports(reports: reports)
        }
        
    }
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        
        guard isConstraintCreationNeeded else {
            return
        }
        isConstraintCreationNeeded = false
        
        view.addSubview(grabberView)
        view.addSubview(titleLabel)
        view.addSubview(_scrollView)
        _scrollView.addSubview(stackView)
        view.addSubview(separatorView)
        view.addSubview(doneButton)
        
        let safeArea = view.window?.safeAreaInsets ?? .zero
        bottomInset = safeArea.bottom > Constants.rootEdge
                        ? 0
                        : min(Constants.rootEdge, abs(safeArea.bottom - Constants.rootEdge))
        
        NSLayoutConstraint.activate([
            grabberView.widthAnchor.constraint(equalToConstant: Constants.grabberWidth),
            grabberView.heightAnchor.constraint(equalToConstant: Constants.grabberHeight),
            grabberView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            grabberView.bottomAnchor.constraint(
                equalTo: titleLabel.topAnchor,
                constant: -Constants.grabberBottomInset
            ),
            
            titleLabel.topAnchor.constraint(
                equalTo: view.topAnchor,
                constant: Constants.rootTop
            ),
            titleLabel.bottomAnchor.constraint(
                equalTo: _scrollView.topAnchor,
                constant: -Constants.grabberBottomInset
            ),
            titleLabel.leadingAnchor.constraint(
                equalTo: view.leadingAnchor,
                constant: Constants.rootEdge
            ),
            titleLabel.trailingAnchor.constraint(
                equalTo: view.trailingAnchor,
                constant: -Constants.rootEdge
            ),
            
            
            _scrollView.leadingAnchor.constraint(
                equalTo: view.leadingAnchor,
                constant: Constants.rootEdge
            ),
            _scrollView.trailingAnchor.constraint(
                equalTo: view.trailingAnchor,
                constant: -Constants.rootEdge
            ),
            
            stackView.topAnchor.constraint(equalTo: _scrollView.topAnchor),
            stackView.bottomAnchor.constraint(equalTo: _scrollView.bottomAnchor),
            stackView.leadingAnchor.constraint(equalTo: _scrollView.leadingAnchor),
            stackView.trailingAnchor.constraint(equalTo: _scrollView.trailingAnchor),
            stackView.widthAnchor.constraint(
                equalTo: view.widthAnchor,
                constant: -(Constants.rootEdge * 2)
            ),
            
            separatorView.topAnchor.constraint(
                equalTo: _scrollView.bottomAnchor,
                constant: Constants.grabberBottomInset
            ),
            separatorView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            separatorView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            separatorView.heightAnchor.constraint(equalToConstant: 1),
            
            doneButton.topAnchor.constraint(equalTo: separatorView.bottomAnchor, constant: 8),
            doneButton.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            doneButton.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            doneButton.heightAnchor.constraint(equalToConstant: 32),
            doneButton.bottomAnchor.constraint(
                equalTo: view.safeAreaLayoutGuide.bottomAnchor, constant: -bottomInset
            ),
        ])
    }
    
    private func setupCamera(camera: MapEventCamera) {
        addLabel(with: camera.name)
        addLabel(with: camera.nativeCameraType.title)
        
        let formatter = Foundation.DateFormatter()
        formatter.locale = Locale(identifier: "en_US_POSIX")
        formatter.timeZone = TimeZone(abbreviation: "GMT+3:00")
        formatter.dateFormat = "dd.MM.yyy"
        
        let time = TimeInterval(camera.updateTime / 1000)
        let date = Date(timeIntervalSince1970: time)
        let dateStr = formatter.string(from: date)
        addLabel(with: "Обновлено: \(dateStr)")
        
        addLabel(with: "Легковая: \(camera.speedCar)")
        addLabel(with: "Грузовая: \(camera.speedTruck)")
    }
    
    private func setupReports(reports: MapEventReports) {
        titleLabel.text = reports.dialogTitle
        
        for msg in reports.messages {
            addReport(with: msg)
        }
    }
    
    private func addLabel(with text: String) {
        let label = UILabel()
        label.text = text
        label.numberOfLines = 0
        stackView.addArrangedSubview(label)
    }
    
    private func addReport(with message: MessageItem) {
        let itemView = DetailsItemView(message: message)
        stackView.addArrangedSubview(itemView)
    }
}

// MARK: - ScrollableBottomSheetPresentedController

extension ReportDetailsViewController: ScrollableBottomSheetPresentedController {
    var scrollView: UIScrollView? {
        _scrollView
    }
}
