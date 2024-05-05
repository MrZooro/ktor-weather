package ru.weather.asyncan.client.dataClasses.forecastWeather

import kotlinx.serialization.Serializable
import ru.weather.asyncan.client.dataClasses.currentWeather.Coord

@Serializable
data class City(
    val coord: Coord? = null,
    val country: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val population: Int? = null,
    val sunrise: Int? = null,
    val sunset: Int? = null,
    val timezone: Int? = null
)