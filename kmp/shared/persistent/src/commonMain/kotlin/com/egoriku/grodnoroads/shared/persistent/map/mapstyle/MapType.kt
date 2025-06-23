package com.egoriku.grodnoroads.shared.persistent.map.mapstyle

enum class MapType(val type: Int) {
    Normal(0),
    Satellite(1),
    Hybrid(2);

    companion object {
        fun toMapType(type: Int?): MapType = entries.find { it.type == type } ?: Normal
    }
}
