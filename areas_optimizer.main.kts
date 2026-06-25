#!/usr/bin/env -S kotlin -Xplugin=/opt/homebrew/opt/kotlin/libexec/lib/kotlinx-serialization-compiler-plugin.jar
// brew install kotlin

@file:DependsOn("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")

import java.io.File
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

val json = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
}

val input = File("areas.geojson")
val output = File("kmp/compose/resources/src/commonMain/composeResources/files/areas.geojson")

val areas = json
    .decodeFromString<AreasDTO>(input.readText())
    .features.map {
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
output.writeText(updatedAreas)
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
