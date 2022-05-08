package com.egoriku.grodnoroads.koin

import com.egoriku.grodnoroads.data.api.SHEET_ROAD_EVENTS
import com.egoriku.grodnoroads.data.api.SHEET_STATIONARY_CAMERA
import com.egoriku.retrosheetkmm.ktor.defaultSheetUrl
import com.egoriku.retrosheetkmm.ktor.plugin.RetrosheetPlugin
import com.egoriku.retrosheetkmm.ktor.plugin.configuration
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val retrosheetModule = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }

            defaultRequest {
                defaultSheetUrl(docId = "1b-R3WMGRzz48QO-zznHQAY7O7DsPM18DmwoLyPoV7Y8")
            }
            install(RetrosheetPlugin) {
                configuration {
                    sheet {
                        name = SHEET_ROAD_EVENTS
                        columns = listOf("type", "message", "latitude", "longitude", "added_time")
                    }
                    sheet {
                        name = SHEET_STATIONARY_CAMERA
                        columns = listOf("message", "latitude", "longitude", "speed", "last_update_time")
                    }
                }
            }
        }
    }
}