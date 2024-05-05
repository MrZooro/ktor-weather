package ru.weather.asyncan.plugins.dataClasses

import kotlinx.serialization.Serializable
import ru.weather.asyncan.client.dataClasses.currentWeather.ClientCurrentWeather
import ru.weather.asyncan.client.dataClasses.forecastWeather.ForecastWeatherItem
import java.text.SimpleDateFormat
import java.util.*

@Serializable
data class ForecastWeather(
    val temp: Int?,
    val humidity: Int?,
    val dt: Int?,
    val dtTxt: String?
) {

    companion object {

        fun formForecastWeatherItem(
            forecastWeatherItem: ForecastWeatherItem,
            timeFormat: SimpleDateFormat,
            date: Date
        ): ForecastWeather {

            val temp = forecastWeatherItem.main?.temp?.toInt()
            val humidity = forecastWeatherItem.main?.humidity
            val dt = forecastWeatherItem.dt

            val dtTxt = if (dt != null) {
                dtToTxt(dt, timeFormat, date)
            } else null

            return ForecastWeather(temp, humidity, dt, dtTxt)
        }

        fun fromRetrofitCurrentWeather(
            retrofitCurrentWeather: ClientCurrentWeather,
            timeFormat: SimpleDateFormat,
            date: Date
        ): ForecastWeather {

            val temp = retrofitCurrentWeather.main?.temp?.toInt()
            val humidity = retrofitCurrentWeather.main?.humidity
            val dt = retrofitCurrentWeather.dt

            val dtTxt = if (dt != null) {
                dtToTxt(dt, timeFormat, date)
            } else null

            return ForecastWeather(temp, humidity, dt, dtTxt)
        }

        fun dtToTxt(
            dt: Int,
            dateFormat: SimpleDateFormat,
            date: Date
        ): String {

            date.time = dt * 1000L
            return dateFormat.format(date)
        }
    }
}
