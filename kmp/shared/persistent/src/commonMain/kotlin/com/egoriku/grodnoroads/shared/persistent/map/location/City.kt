package com.egoriku.grodnoroads.shared.persistent.map.location

import com.egoriku.grodnoroads.location.LatLng

enum class City(val cityName: String, val latLng: LatLng) {
    Berestovitca(cityName = "berestovitca", latLng = LatLng(53.191924, 24.018453)),
    Berezovka(cityName = "berezovka", latLng = LatLng(53.718250, 25.500274)),
    Dyatlovo(cityName = "dyatlovo", latLng = LatLng(53.465115, 25.407569)),
    Grodno(cityName = "grodno", latLng = LatLng(53.6687765, 23.8212226)),
    Indura(cityName = "indura", latLng = LatLng(53.459580, 23.883849)),
    Ivie(cityName = "ivie", latLng = LatLng(53.930905, 25.770078)),
    Korelichi(cityName = "korelichi", latLng = LatLng(53.570510, 26.139959)),
    Lida(cityName = "lida", latLng = LatLng(53.891667, 25.302254)),
    Mosty(cityName = "mosty", latLng = LatLng(53.413523, 24.542953)),
    Novogrudok(cityName = "novogrudok", latLng = LatLng(53.598095, 25.825635)),
    Ostrovec(cityName = "ostrovec", latLng = LatLng(54.615366, 25.958338)),
    Ozery(cityName = "ozery", latLng = LatLng(53.722526, 24.178165)),
    Porechye(cityName = "porechye", latLng = LatLng(53.885623, 24.137678)),
    Shuchin(cityName = "shuchin", latLng = LatLng(53.604412, 24.742567)),
    Skidel(cityName = "skidel", latLng = LatLng(53.579644, 24.237978)),
    Slonim(cityName = "slonim", latLng = LatLng(53.092705, 25.319268)),
    Svisloch(cityName = "svisloch", latLng = LatLng(53.035845, 24.094495)),
    Volkovysk(cityName = "volkovysk", latLng = LatLng(53.152847, 24.444242)),
    Voronovo(cityName = "voronovo", latLng = LatLng(54.152229, 25.319313)),
    Zelva(cityName = "zelva", latLng = LatLng(53.149502, 24.817092)),

    // Brest region
    Ruzhany(cityName = "ruzhany", latLng = LatLng(52.866458, 24.890205));

    companion object {
        fun toCity(value: String) = checkNotNull(entries.find { it.cityName == value })
    }
}