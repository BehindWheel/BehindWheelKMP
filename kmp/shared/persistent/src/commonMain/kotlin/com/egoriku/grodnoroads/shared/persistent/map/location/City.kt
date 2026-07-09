package com.egoriku.grodnoroads.shared.persistent.map.location

import com.egoriku.grodnoroads.location.LatLng

enum class City(val cityName: String, val latLng: LatLng, val defaultZoom: Float) {
    Berestovitca(
        cityName = "berestovitca",
        latLng = LatLng(53.191924, 24.018453),
        defaultZoom = 14.6f
    ),
    Berezovka(
        cityName = "berezovka",
        latLng = LatLng(53.718250, 25.500274),
        defaultZoom = 14.4f
    ),
    Dyatlovo(
        cityName = "dyatlovo",
        latLng = LatLng(53.465115, 25.407569),
        defaultZoom = 14.3f
    ),
    Grodno(
        cityName = "grodno",
        latLng = LatLng(53.6687765, 23.8212226),
        defaultZoom = 12.5f
    ),
    Indura(
        cityName = "indura",
        latLng = LatLng(53.459580, 23.883849),
        defaultZoom = 15.2f
    ),
    Ivie(
        cityName = "ivie",
        latLng = LatLng(53.930905, 25.770078),
        defaultZoom = 14.1f
    ),
    Korelichi(
        cityName = "korelichi",
        latLng = LatLng(53.570510, 26.139959),
        defaultZoom = 14.5f
    ),
    Lida(
        cityName = "lida",
        latLng = LatLng(53.891667, 25.302254),
        defaultZoom = 12.8f
    ),
    Mosty(
        cityName = "mosty",
        latLng = LatLng(53.413523, 24.542953),
        defaultZoom = 13.4f
    ),
    Novogrudok(
        cityName = "novogrudok",
        latLng = LatLng(53.598095, 25.825635),
        defaultZoom = 14.5f
    ),
    Ostrovec(
        cityName = "ostrovec",
        latLng = LatLng(54.615366, 25.958338),
        defaultZoom = 13.4f
    ),
    Ozery(
        cityName = "ozery",
        latLng = LatLng(53.722526, 24.178165),
        defaultZoom = 14.0f
    ),
    Porechye(
        cityName = "porechye",
        latLng = LatLng(53.884536, 24.134896),
        defaultZoom = 14.6f
    ),
    Shuchin(
        cityName = "shuchin",
        latLng = LatLng(53.604270, 24.733359),
        defaultZoom = 13.6f
    ),
    Skidel(
        cityName = "skidel",
        latLng = LatLng(53.581465, 24.228489),
        defaultZoom = 12.8f
    ),
    Slonim(
        cityName = "slonim",
        latLng = LatLng(53.092705, 25.319268),
        defaultZoom = 12.9f
    ),
    Svisloch(
        cityName = "svisloch",
        latLng = LatLng(53.035845, 24.094495),
        defaultZoom = 14.5f
    ),
    Volkovysk(
        cityName = "volkovysk",
        latLng = LatLng(53.152847, 24.444242),
        defaultZoom = 13.0f
    ),
    Voronovo(
        cityName = "voronovo",
        latLng = LatLng(54.150707, 25.313543),
        defaultZoom = 13.9f
    ),
    Zelva(
        cityName = "zelva",
        latLng = LatLng(53.149223, 24.813549),
        defaultZoom = 14.3f
    ),

    // Brest region
    Ruzhany(cityName = "ruzhany", latLng = LatLng(52.866458, 24.890205), defaultZoom = 14.5f);

    companion object {
        fun toCity(value: String) = checkNotNull(entries.find { it.cityName == value })
    }
}
