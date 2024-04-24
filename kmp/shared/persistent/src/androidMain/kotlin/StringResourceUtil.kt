package com.egoriku.grodnoroads.shared.persistent

import com.egoriku.grodnoroads.resources.MR
import com.egoriku.grodnoroads.shared.persistent.appearance.Language
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme
import com.egoriku.grodnoroads.shared.persistent.map.filtering.Filtering
import com.egoriku.grodnoroads.shared.persistent.map.location.City

fun Language.toStringResource() = when (this) {
    Language.Russian -> MR.strings.appearance_app_language_ru
    Language.English -> MR.strings.appearance_app_language_en
    Language.Belarusian -> MR.strings.appearance_app_language_be
    Language.System -> MR.strings.appearance_app_language_system
}

fun Theme.toStringResource() = when (this) {
    Theme.System -> MR.strings.appearance_app_theme_system
    Theme.Dark -> MR.strings.appearance_app_theme_dark
    Theme.Light -> MR.strings.appearance_app_theme_light
}

fun City.toStringResource() = when (this) {
    City.Grodno -> MR.strings.map_default_location_grodno
    City.Skidel -> MR.strings.map_default_location_skidel
    City.Volkovysk -> MR.strings.map_default_location_volkovysk
    City.Ozery -> MR.strings.map_default_location_ozery
    City.Porechye -> MR.strings.map_default_location_porechye
    City.Berestovitca -> MR.strings.map_default_location_berestovitca
    City.Shuchin -> MR.strings.map_default_location_shuchin
    City.Mosty -> MR.strings.map_default_location_mosty
    City.Slonim -> MR.strings.map_default_location_slonim
    City.Lida -> MR.strings.map_default_location_lida
    City.Indura -> MR.strings.map_default_location_indura
    City.Dyatlovo -> MR.strings.map_default_location_dyatlovo
    City.Novogrudok -> MR.strings.map_default_location_novogrudok
    City.Korelichi -> MR.strings.map_default_location_korelichi
}

fun Filtering.toStringResource() = when (this) {
    Filtering.Minutes15 -> MR.strings.map_markers_filtering_15_minutes
    Filtering.Minutes30 -> MR.strings.map_markers_filtering_30_minutes
    Filtering.Minutes45 -> MR.strings.map_markers_filtering_45_minutes
    Filtering.Hours1 -> MR.strings.map_markers_filtering_1_hour
}