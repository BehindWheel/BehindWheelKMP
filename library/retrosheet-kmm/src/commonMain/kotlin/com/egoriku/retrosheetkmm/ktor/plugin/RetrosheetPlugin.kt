package com.egoriku.retrosheetkmm.ktor.plugin

import com.egoriku.retrosheetkmm.ktor.iGoogleSheetUrl
import com.egoriku.retrosheetkmm.ktor.replace
import com.egoriku.retrosheetkmm.parseCsv
import com.egoriku.retrosheetkmm.util.QueryConverter
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.util.*
import io.ktor.util.reflect.*
import io.ktor.utils.io.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import java.nio.charset.Charset

class RetrosheetPlugin(private val sheets: Map<String, Map<String, String>>) {

    class Config {
        var configuration: Configuration = Configuration(sheets = emptyMap())
    }

    companion object Feature : HttpClientPlugin<Config, RetrosheetPlugin> {

        private val converter = KotlinxSerializationConverter(
            Json {
                ignoreUnknownKeys = true
            }
        )

        override val key: AttributeKey<RetrosheetPlugin> = AttributeKey("retrosheet-kmm")

        override fun prepare(block: Config.() -> Unit): RetrosheetPlugin {
            val config = Config().apply(block)

            return RetrosheetPlugin(sheets = config.configuration.sheets)
        }

        override fun install(plugin: RetrosheetPlugin, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.Before) {
                val urlBuilder = context.url

                when {
                    urlBuilder.iGoogleSheetUrl -> {
                        context.url {
                            encodedPath = encodedPath.plus("/gviz/tq")

                            parameters.append("tqx", "out:csv")

                            val sheetName = requireNotNull(urlBuilder.parameters["sheet"])

                            val query = urlBuilder.parameters["tq"]

                            if (!query.isNullOrEmpty()) {
                                val escapedQuery = QueryConverter(
                                    smartQuery = query,
                                    smartQueryMap = requireNotNull(plugin.sheets[sheetName]) {
                                        "Couldn't find queryMap for '$sheetName'"
                                    },
                                ).convert()

                                parameters.replace("tq", escapedQuery)
                            }
                        }

                        println(context.url.buildString())
                        proceedWith(it)
                    }
                    else -> return@intercept
                }
            }

            scope.responsePipeline.intercept(HttpResponsePipeline.Receive) { (info: TypeInfo, body: Any) ->
                if (!context.request.url.iGoogleSheetUrl()) return@intercept
                if (body !is ByteReadChannel) return@intercept

                val csvString = parseCsv(data = body.readRemaining().readText())

                val encodedJson = Json.encodeToString(
                    serializer = ListSerializer(
                        elementSerializer = MapSerializer(
                            String.serializer(),
                            String.serializer()
                        )
                    ),
                    value = csvString
                )

                val response = converter.deserialize(
                    charset = Charset.defaultCharset() as io.ktor.utils.io.charsets.Charset,
                    typeInfo = info,
                    content = ByteReadChannel(encodedJson)
                ) ?: return@intercept

                val subject = HttpResponseContainer(
                    expectedType = info,
                    response = response
                )

                proceedWith(subject)
            }
        }
    }
}