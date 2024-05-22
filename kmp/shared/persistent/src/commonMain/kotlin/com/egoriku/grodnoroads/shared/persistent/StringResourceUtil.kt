package com.egoriku.grodnoroads.shared.persistent

import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.appearance_app_language_be
import com.egoriku.grodnoroads.compose.resources.appearance_app_language_en
import com.egoriku.grodnoroads.compose.resources.appearance_app_language_ru
import com.egoriku.grodnoroads.compose.resources.appearance_app_language_system
import com.egoriku.grodnoroads.compose.resources.appearance_app_theme_dark
import com.egoriku.grodnoroads.compose.resources.appearance_app_theme_light
import com.egoriku.grodnoroads.compose.resources.appearance_app_theme_system
import com.egoriku.grodnoroads.compose.resources.map_default_location_berestovitca
import com.egoriku.grodnoroads.compose.resources.map_default_location_berezovka
import com.egoriku.grodnoroads.compose.resources.map_default_location_dyatlovo
import com.egoriku.grodnoroads.compose.resources.map_default_location_grodno
import com.egoriku.grodnoroads.compose.resources.map_default_location_indura
import com.egoriku.grodnoroads.compose.resources.map_default_location_ivie
import com.egoriku.grodnoroads.compose.resources.map_default_location_korelichi
import com.egoriku.grodnoroads.compose.resources.map_default_location_lida
import com.egoriku.grodnoroads.compose.resources.map_default_location_mosty
import com.egoriku.grodnoroads.compose.resources.map_default_location_novogrudok
import com.egoriku.grodnoroads.compose.resources.map_default_location_ostrovec
import com.egoriku.grodnoroads.compose.resources.map_default_location_ozery
import com.egoriku.grodnoroads.compose.resources.map_default_location_porechye
import com.egoriku.grodnoroads.compose.resources.map_default_location_ruzhany
import com.egoriku.grodnoroads.compose.resources.map_default_location_shuchin
import com.egoriku.grodnoroads.compose.resources.map_default_location_skidel
import com.egoriku.grodnoroads.compose.resources.map_default_location_slonim
import com.egoriku.grodnoroads.compose.resources.map_default_location_svisloch
import com.egoriku.grodnoroads.compose.resources.map_default_location_volkovysk
import com.egoriku.grodnoroads.compose.resources.map_default_location_voronovo
import com.egoriku.grodnoroads.compose.resources.map_default_location_zelva
import com.egoriku.grodnoroads.compose.resources.map_markers_filtering_15_minutes
import com.egoriku.grodnoroads.compose.resources.map_markers_filtering_1_hour
import com.egoriku.grodnoroads.compose.resources.map_markers_filtering_30_minutes
import com.egoriku.grodnoroads.compose.resources.map_markers_filtering_45_minutes
import com.egoriku.grodnoroads.shared.persistent.appearance.Language
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme
import com.egoriku.grodnoroads.shared.persistent.map.filtering.Filtering
import com.egoriku.grodnoroads.shared.persistent.map.location.City

fun Language.toStringResource() = when (this) {
    Language.Russian -> Res.string.appearance_app_language_ru
    Language.English -> Res.string.appearance_app_language_en
    Language.Belarusian -> Res.string.appearance_app_language_be
    Language.System -> Res.string.appearance_app_language_system
}

fun Theme.toStringResource() = when (this) {
    Theme.System -> Res.string.appearance_app_theme_system
    Theme.Dark -> Res.string.appearance_app_theme_dark
    Theme.Light -> Res.string.appearance_app_theme_light
}

fun City.toStringResource() = when (this) {
    City.Berestovitca -> Res.string.map_default_location_berestovitca
    City.Berezovka -> Res.string.map_default_location_berezovka
    City.Dyatlovo -> Res.string.map_default_location_dyatlovo
    City.Grodno -> Res.string.map_default_location_grodno
    City.Indura -> Res.string.map_default_location_indura
    City.Ivie -> Res.string.map_default_location_ivie
    City.Korelichi -> Res.string.map_default_location_korelichi
    City.Lida -> Res.string.map_default_location_lida
    City.Mosty -> Res.string.map_default_location_mosty
    City.Novogrudok -> Res.string.map_default_location_novogrudok
    City.Ostrovec -> Res.string.map_default_location_ostrovec
    City.Ozery -> Res.string.map_default_location_ozery
    City.Porechye -> Res.string.map_default_location_porechye
    City.Ruzhany -> Res.string.map_default_location_ruzhany
    City.Shuchin -> Res.string.map_default_location_shuchin
    City.Skidel -> Res.string.map_default_location_skidel
    City.Slonim -> Res.string.map_default_location_slonim
    City.Svisloch -> Res.string.map_default_location_svisloch
    City.Volkovysk -> Res.string.map_default_location_volkovysk
    City.Voronovo -> Res.string.map_default_location_voronovo
    City.Zelva -> Res.string.map_default_location_zelva
}

fun Filtering.toStringResource() = when (this) {
    Filtering.Minutes15 -> Res.string.map_markers_filtering_15_minutes
    Filtering.Minutes30 -> Res.string.map_markers_filtering_30_minutes
    Filtering.Minutes45 -> Res.string.map_markers_filtering_45_minutes
    Filtering.Hours1 -> Res.string.map_markers_filtering_1_hour
}