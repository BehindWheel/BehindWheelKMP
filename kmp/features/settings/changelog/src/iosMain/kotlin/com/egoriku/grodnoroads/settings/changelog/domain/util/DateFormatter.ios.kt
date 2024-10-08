package com.egoriku.grodnoroads.settings.changelog.domain.util

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.localeIdentifier
import platform.Foundation.localeWithLocaleIdentifier

actual class DateFormatter {
    actual fun formatTime(timeLong: Long): String {
        val date = NSDate.dateWithTimeIntervalSince1970(timeLong.toDouble())
        val dateFormatter = NSDateFormatter().apply {
            dateFormat = DATE_FORMAT_PATTERN
            locale = NSLocale.localeWithLocaleIdentifier(NSLocale.currentLocale.localeIdentifier)
        }
        return dateFormatter.stringFromDate(date)
    }
}
