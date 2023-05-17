package com.egoriku.grodnoroads.shared.appsettings.types.map.location

import com.egoriku.grodnoroads.resources.R
import com.google.android.gms.maps.model.LatLng

enum class City(val cityName: String, val latLng: LatLng) {
    Grodno(cityName = "grodno", latLng = LatLng(53.6687765, 23.8212226)),
    Skidel(cityName = "skidel", latLng = LatLng(53.579644, 24.237978)),
    Volkovysk(cityName = "volkovysk", latLng = LatLng(53.152847, 24.444242)),
    Ozery(cityName = "ozery", latLng = LatLng(53.722526, 24.178165)),
    Porechye(cityName = "porechye", latLng = LatLng(53.885623, 24.137678)),
    Berestovitca(cityName = "berestovitca", latLng = LatLng(53.191924, 24.018453)),
    Shuchin(cityName = "shuchin", latLng = LatLng(53.604412, 24.742567)),
    Mosty(cityName = "mosty", latLng = LatLng(53.413523, 24.542953)),
    Slonim(cityName = "slonim", latLng = LatLng(53.092705, 25.319268)),
    Lida(cityName = "lida", latLng = LatLng(53.891667, 25.302254)),
    Indura(cityName = "indura", latLng = LatLng(53.459580, 23.883849)),
    Dyatlovo(cityName = "dyatlovo", latLng = LatLng(53.465115, 25.407569)),
    Novogrudok(cityName = "novogrudok", latLng = LatLng(53.598095, 25.825635)),
    Korelichi(cityName = "korelichi", latLng = LatLng(53.570510, 26.139959));

    companion object {
        val supportedCities = listOf(
            Berestovitca,
            Volkovysk,
            Grodno,
            Dyatlovo,
            Indura,
            Korelichi,
            Lida,
            Mosty,
            Novogrudok,
            Ozery,
            Porechye,
            Skidel,
            Slonim,
            Shuchin,
        )

        fun toCity(value: String) =
            checkNotNull(values().find { it.cityName == value })

        fun City.toResource(): Int = when (this) {
            Grodno -> R.string.map_default_location_grodno
            Skidel -> R.string.map_default_location_skidel
            Volkovysk -> R.string.map_default_location_volkovysk
            Ozery -> R.string.map_default_location_ozery
            Porechye -> R.string.map_default_location_porechye
            Berestovitca -> R.string.map_default_location_berestovitca
            Shuchin -> R.string.map_default_location_shuchin
            Mosty -> R.string.map_default_location_mosty
            Slonim -> R.string.map_default_location_slonim
            Lida -> R.string.map_default_location_lida
            Indura -> R.string.map_default_location_indura
            Dyatlovo -> R.string.map_default_location_dyatlovo
            Novogrudok -> R.string.map_default_location_novogrudok
            Korelichi -> R.string.map_default_location_korelichi
        }
    }
}