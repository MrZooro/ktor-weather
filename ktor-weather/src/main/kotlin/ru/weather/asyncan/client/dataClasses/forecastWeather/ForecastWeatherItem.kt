package ru.weather.asyncan.client.dataClasses.forecastWeather

import kotlinx.serialization.Serializable
import ru.weather.asyncan.client.dataClasses.currentWeather.Clouds
import ru.weather.asyncan.client.dataClasses.currentWeather.Main
import ru.weather.asyncan.client.dataClasses.currentWeather.Sys
import ru.weather.asyncan.client.dataClasses.currentWeather.Weather
import ru.weather.asyncan.client.dataClasses.currentWeather.Wind

@Serializable
data class ForecastWeatherItem(
    val dt : Int? = null,
    val main: Main? = null,
    val weather: List<Weather>? = null,
    val clouds: Clouds? = null,
    val wind: Wind? = null,
    val visibility: Int? = null,
    val pop: Double? = null,
    val rain : Rain? = null,
    val sys: Sys? = null,
    var dt_txt : String? = null
)