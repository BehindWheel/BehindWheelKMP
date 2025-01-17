package com.egoriku.grodnoroads.guidance.screen.ui.google

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.egoriku.grodnoroads.maps.compose.extension.MarkerImage
import kotlin.math.PI
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGPointMake
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGRectZero
import platform.QuartzCore.CAShapeLayer
import platform.UIKit.UIBezierPath
import platform.UIKit.UIColor
import platform.UIKit.UIFont
import platform.UIKit.UIGraphicsImageRenderer
import platform.UIKit.UIImage
import platform.UIKit.UILabel
import platform.UIKit.UIView

actual class MarkerGenerator {
    actual fun makeIcon(text: String): MarkerImage = MapReportView(text).snapshot
}

@Composable
actual fun rememberMarkerGenerator(): MarkerGenerator {
    return remember { MarkerGenerator() }
}

@OptIn(ExperimentalForeignApi::class)
class MapReportView(text: String) : UIView(frame = CGRectZero.readValue()) {
    private val maxLabelWidth = 300.0

    private val titleLabel by lazy {
        UILabel().apply {
            textColor = UIColor.grayColor
            font = UIFont.systemFontOfSize(15.0)
            sizeToFit()
        }
    }

    init {
        val backgroundView = UIView()
        addSubview(backgroundView)

        titleLabel.text = text
        titleLabel.sizeToFit()

        val labelWidth = titleLabel.bounds.useContents { size.width }

        if (labelWidth > maxLabelWidth) {
            titleLabel.setFrame(
                titleLabel.frame.useContents {
                    CGRectMake(
                        x = origin.x,
                        y = origin.y,
                        width = maxLabelWidth,
                        height = size.height
                    )
                }
            )
        }

        setFrame(
            CGRectMake(
                x = 0.0,
                y = 0.0,
                width = titleLabel.bounds.useContents { size.width + 16 },
                height = titleLabel.bounds.useContents { size.height + 24 }
            )
        )
        backgroundView.setFrame(frame)

        addSubview(titleLabel)
        titleLabel.setFrame(
            titleLabel.frame.useContents {
                CGRectMake(8.0, 8.0, size.width, size.height)
            }
        )

        val width = frame.useContents { size.width - 2 }
        val height = frame.useContents { size.height - 10 }
        val centerX = frame.useContents { size.width / 2f }

        val path = UIBezierPath()
        path.moveToPoint(CGPointMake(0.0, 4.0))
        path.addArcWithCenter(
            center = CGPointMake(x = 4.0, y = 4.0),
            radius = 4.0,
            startAngle = PI,
            endAngle = 3 * PI / 2,
            clockwise = true
        )
        path.addLineToPoint(CGPointMake(x = width - 4.0, y = 0.0))
        path.addArcWithCenter(
            center = CGPointMake(x = width - 4.0, y = 4.0),
            radius = 4.0,
            startAngle = 3 * PI / 2,
            endAngle = 0.0,
            clockwise = true
        )
        path.addLineToPoint(CGPointMake(x = width, y = height - 4.0))
        path.addArcWithCenter(
            center = CGPointMake(x = width - 4, y = height - 4),
            radius = 4.0,
            startAngle = 0.0,
            endAngle = PI / 2,
            clockwise = true
        )
        path.addLineToPoint(CGPointMake(x = centerX + 8, y = height))
        path.addLineToPoint(CGPointMake(x = centerX, y = height + 8))
        path.addLineToPoint(CGPointMake(x = centerX - 8, y = height))
        path.addLineToPoint(CGPointMake(x = 4.0, y = height))
        path.addArcWithCenter(
            center = CGPointMake(x = 4.0, y = height - 4),
            radius = 4.0,
            startAngle = PI / 2,
            endAngle = PI,
            clockwise = true
        )
        path.closePath()

        val shape = CAShapeLayer()
        shape.fillColor = UIColor.whiteColor.CGColor
        shape.strokeColor = UIColor.lightGrayColor.CGColor
        shape.lineWidth = 1.0
        shape.position = CGPointMake(x = 1.0, y = 1.0)
        shape.path = path.CGPath

        backgroundView.layer.addSublayer(shape)
    }
}

@OptIn(ExperimentalForeignApi::class)
private val UIView.snapshot: UIImage
    get() {
        val renderer = UIGraphicsImageRenderer(bounds = bounds)
        val image = renderer.imageWithActions {
            drawViewHierarchyInRect(rect = bounds, afterScreenUpdates = true)
        }
        return image
    }
