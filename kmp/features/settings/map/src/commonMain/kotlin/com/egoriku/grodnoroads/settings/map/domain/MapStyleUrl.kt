package com.egoriku.grodnoroads.settings.map.domain

object MapStyleUrl {
    private const val BASE_URL = "https://firebasestorage.googleapis.com/v0/b/grodno-roads.appspot.com"

    const val LIGHT_MINIMAL = "$BASE_URL/o/permanent%2Fmap_settings_light_minimal.png?alt=media&token=ef09c65b-5979-4701-87b1-5043dc8902e9"
    const val LIGHT_DETAILED = "$BASE_URL/o/permanent%2Fmap_settings_light_detailed.png?alt=media&token=3361e144-75e7-4697-807c-fb56ea0cf467"

    const val DARK_MINIMAL = "$BASE_URL/o/permanent%2Fmap_settings_dark_minimal.png?alt=media&token=519e73a8-fe81-42a4-9214-1051c06898b1"
    const val DARK_DETAILED = "$BASE_URL/o/permanent%2Fmap_settings_dark_detailed.png?alt=media&token=12855714-e298-445a-b38a-098ebde5bf2a"
}
