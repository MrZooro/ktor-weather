package ru.weather.asyncan.client.dataClasses.currentWeather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rain(
    @SerialName("1h")
    val h: Double? = null,
    @SerialName("3h")
    val hhh: Double? = null
)