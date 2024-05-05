package ru.weather.asyncan.plugins

import io.github.smiley4.ktorswaggerui.dsl.get
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import ru.weather.asyncan.client.WeatherClient
import ru.weather.asyncan.plugins.dataClasses.ForecastWeather
import ru.weather.asyncan.plugins.dataClasses.MainWeatherInfo

fun Application.configureRouting(database: CoroutineDatabase, weatherClient: WeatherClient) {

    val apiKeyParameter = "apiKey"
    val latitudeParameter = "lat"
    val longitudeParameter = "lon"

    routing {

        get("/", {
            description = "Hello World Endpoint."
            response {
                HttpStatusCode.OK to {
                    description = "Successful Request"
                    body<String> { description = "the response" }
                }
                HttpStatusCode.InternalServerError to {
                    description = "Something unexpected happened"
                }
            }
        }) {

            call.respondText("Hello World!")
        }

        //getCurrentWeather
        //lat & lan
        get("/weather/current", {
            description = "Текущий прогноз погоды"
            request {
                headerParameter<String>(apiKeyParameter)
                queryParameter<Double>(latitudeParameter)
                queryParameter<Double>(longitudeParameter)
            }
            response {
                HttpStatusCode.OK to {
                    description = "Successful Request"
                    body<MainWeatherInfo> { description = "the response" }
                }
                HttpStatusCode.InternalServerError to {
                    description = "Something unexpected happened"
                }
            }
        }) {
            val apiKey = call.request.headers[apiKeyParameter] ?: ""
            val lat = call.request.queryParameters[latitudeParameter]?.toDouble() ?: 55.751244
            val lon = call.request.queryParameters[longitudeParameter]?.toDouble() ?: 37.618423
            val currentWeather: MainWeatherInfo = weatherClient.getCurrentWeather(apiKey, lat, lon)

            call.respond(currentWeather)
        }

        //getForecastWeather
        //lat & lan
        get("/weather/forecast", {
            description = "Будущий прогноз погоды"
            request {
                headerParameter<String>(apiKeyParameter)
                queryParameter<Double>(latitudeParameter)
                queryParameter<Double>(longitudeParameter)
            }
            response {
                HttpStatusCode.OK to {
                    description = "Successful Request"
                    body<List<ForecastWeather>> { description = "the response" }
                }
                HttpStatusCode.InternalServerError to {
                    description = "Something unexpected happened"
                }
            }
        }) {
            val apiKey = call.request.headers[apiKeyParameter] ?: ""
            val lat = call.request.queryParameters[latitudeParameter]?.toDouble() ?: 55.751244
            val lon = call.request.queryParameters[longitudeParameter]?.toDouble() ?: 37.618423
            val forecastWeather: List<ForecastWeather> = weatherClient.getForecastWeather(apiKey, lat, lon)

            call.respond(forecastWeather)
        }
    }
}
