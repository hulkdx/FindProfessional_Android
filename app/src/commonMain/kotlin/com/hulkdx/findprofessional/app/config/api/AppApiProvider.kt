package com.hulkdx.findprofessional.app.config.api

import com.hulkdx.findprofessional.core.config.PlatformSpecific
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json

object AppApiProvider {

    fun httpClient(ps: PlatformSpecific): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json()
            }

            install(DefaultRequest) {
                url(baseUrl(ps))
            }

            // throws exception when request is not successful:
            expectSuccess = true

            if (ps.isDebug()) {
                debugClientConfig()
            }
        }
    }

    private fun HttpClientConfig<*>.debugClientConfig() {
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println("--------------")
                    println(message)
                }
            }
            level = LogLevel.ALL
        }
    }

    private fun baseUrl(ps: PlatformSpecific): String = with(ps) {
        return if (isDebug()) {
            "http://${localhostUrl()}:8080/"
        } else {
            "http://api.sabajafarzadeh.com:30000/"
        }
    }
}
