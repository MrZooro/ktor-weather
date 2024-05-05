package ru.weather.asyncan.client.dataClasses.forecastWeather

import kotlinx.serialization.Serializable

@Serializable
data class ClientForecastWeather(
    val cod : String? = null,
    val message : Int? = null,
    val cnt : Int? = null,
    val list: List<ForecastWeatherItem>? = null,
    val city: City? = null
)

