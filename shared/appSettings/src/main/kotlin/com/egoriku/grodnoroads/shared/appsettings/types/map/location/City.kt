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
    Shuchin(cityName = "shuchin", latLng = LatLng(53.604412, 24.742567));

    companion object {
        val supportedCities = listOf(
            Berestovitca,
            Volkovysk,
            Grodno,
            Ozery,
            Porechye,
            Skidel,
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
        }
    }
}