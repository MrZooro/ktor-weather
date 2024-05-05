package ru.weather.asyncan.client.dataClasses.currentWeather

import kotlinx.serialization.Serializable
import ru.weather.asyncan.client.dataClasses.forecastWeather.ForecastWeatherItem

@Serializable
data class ClientCurrentWeather(
    val base: String? = null,
    val clouds: Clouds? = null,
    val cod: Int? = null,
    val coord: Coord? = null,
    val dt: Int? = null,
    val id: Int? = null,
    val main: Main? = null,
    val name: String? = null,
    val rain: Rain? = null,
    val sys: Sys? = null,
    val timezone: Int? = null,
    val visibility: Int? = null,
    val weather: List<Weather>? = null,
    val wind: Wind? = null,
    var dt_txt: String = ""
) {

    fun toForecastWeatherItem(newDtTxt: String): ForecastWeatherItem {

        return ForecastWeatherItem(dt, main, weather, clouds, wind, visibility, pop = -1.0,
            ru.weather.asyncan.client.dataClasses.forecastWeather.Rain(
                0.0
            ), sys, newDtTxt)
    }
}