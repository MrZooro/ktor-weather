package ru.weather.asyncan.client.dataClasses.currentWeather

import kotlinx.serialization.Serializable

@Serializable
data class Clouds(
    val all: Int? = null
)