#!/usr/bin/env -S kotlin -Xplugin=/opt/homebrew/opt/kotlin/libexec/lib/kotlinx-serialization-compiler-plugin.jar
// brew install kotlin

@file:DependsOn("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.0")

import Areas_optimizer_main.MultiPolygon
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

val json = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
}
val filePath = "kmp/compose/resources/src/commonMain/composeResources/files/areas.geojson"
val jsonData = File(filePath).readText()
val featureCollection = json.decodeFromString<AreasDTO>(jsonData)

val areas = featureCollection.features.map {
    AreaDTO(
        name = it.properties.name,
        coordinates = it.geometry.coordinates.first().first().map { coordinates ->
            LatLng(
                latitude = coordinates[1],
                longitude = coordinates[0]
            )
        }
    )
}

val updatedAreas = json.encodeToString(areas)
File(filePath).writeText(updatedAreas)
println("Updated JSON data: $updatedAreas")

// Old models
typealias MultiPolygon = List<List<List<List<Double>>>>

@Serializable
data class AreasDTO(val type: String, val features: List<Feature>)

@Serializable
data class Feature(val geometry: MultiPolygonGeometry, val properties: Properties)

@Serializable
data class MultiPolygonGeometry(val coordinates: MultiPolygon)

@Serializable
data class Properties(val name: String)

@Serializable
data class AreaDTO(val name: String, val coordinates: List<LatLng>)

@Serializable
data class LatLng(val latitude: Double, val longitude: Double)
