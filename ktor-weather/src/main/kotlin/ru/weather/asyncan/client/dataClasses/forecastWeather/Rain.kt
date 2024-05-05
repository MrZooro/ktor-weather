package ru.weather.asyncan.client.dataClasses.forecastWeather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rain(
    @SerialName("3h")
    val hhh: Double? = null
)