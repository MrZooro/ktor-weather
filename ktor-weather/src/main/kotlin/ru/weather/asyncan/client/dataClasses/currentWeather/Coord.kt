package ru.weather.asyncan.client.dataClasses.currentWeather

import kotlinx.serialization.Serializable

@Serializable
data class Coord(
    val lat: Double? = null,
    val lon: Double? = null
)