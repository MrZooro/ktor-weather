package ru.weather.asyncan.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import ru.weather.asyncan.client.dataClasses.currentWeather.ClientCurrentWeather
import ru.weather.asyncan.client.dataClasses.forecastWeather.ClientForecastWeather
import ru.weather.asyncan.plugins.dataClasses.ForecastWeather
import ru.weather.asyncan.plugins.dataClasses.MainWeatherInfo
import java.text.SimpleDateFormat
import java.util.*

class WeatherClient {

    @OptIn(ExperimentalSerializationApi::class)
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    explicitNulls = false
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    private val enLocale = Locale("en")
    private val mainDateFormat = SimpleDateFormat("EEE, HH:mm", enLocale)
    private val timeDateFormat = SimpleDateFormat("HH:mm", enLocale)
    private val date = Date()

    private val currentBaseUrl = "https://api.openweathermap.org/data/2.5/weather?"
    private val forecastBaseUrl = "https://api.openweathermap.org/data/2.5/forecast?"

    private val apiKeyParameter = "appid"
    private val latitudeParameter = "lat"
    private val longitudeParameter = "lon"

    //val apiKey = System.getenv("apiKey") ?: "cant get api key"

    private suspend fun updateCurrentWeather(
        apiKey: String,
        lat: Double,
        lon: Double
    ): ClientCurrentWeather {

        return httpClient.get {
            url(currentBaseUrl)
            parameter(apiKeyParameter, apiKey)
            parameter(latitudeParameter, lat)
            parameter(longitudeParameter, lon)
        }.body<ClientCurrentWeather>()
    }

    private suspend fun updateForecastWeather(
        apiKey: String,
        lat: Double,
        lon: Double
    ): ClientForecastWeather {

        return httpClient.get {
            url(forecastBaseUrl)
            parameter(apiKeyParameter, apiKey)
            parameter(latitudeParameter, lat)
            parameter(longitudeParameter, lon)
        }.body<ClientForecastWeather>()
    }

    suspend fun getCurrentWeather(
        apiKey: String,
        lat: Double,
        lon: Double
    ): MainWeatherInfo {
        val httpCurrentWeather = updateCurrentWeather(apiKey, lat, lon)

        return MainWeatherInfo.fromRetrofitCurrentWeather(
            httpCurrentWeather,
            mainDateFormat,
            timeDateFormat,
            date
        )
    }

    suspend fun getForecastWeather(
        apiKey: String,
        lat: Double,
        lon: Double
    ): List<ForecastWeather> {
        val httpForecastWeather = updateForecastWeather(apiKey, lat, lon)

        val result: MutableList<ForecastWeather> = mutableListOf()
        if (httpForecastWeather.list != null) {
            httpForecastWeather.list.forEach { forecastWeatherItem ->
                val tempResultItem = ForecastWeather.formForecastWeatherItem(
                    forecastWeatherItem,
                    timeDateFormat,
                    date
                )

                result.add(tempResultItem)
            }
        }
        return result.toList()
    }

}