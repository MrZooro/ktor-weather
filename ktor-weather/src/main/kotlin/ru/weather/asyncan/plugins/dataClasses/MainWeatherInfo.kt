package ru.weather.asyncan.plugins.dataClasses

import kotlinx.serialization.Serializable
import ru.weather.asyncan.client.dataClasses.currentWeather.ClientCurrentWeather
import ru.weather.asyncan.client.dataClasses.forecastWeather.ClientForecastWeather
import ru.weather.asyncan.client.dataClasses.forecastWeather.ForecastWeatherItem
import java.text.SimpleDateFormat
import java.util.*

@Serializable
data class MainWeatherInfo(
    val cityName: String? = null,
    val cityLat: Double? = null,
    val cityLon: Double? = null,
    val dtTxt: String? = null,
    val dt: Int? = null,
    val temp: Int? = null,
    val feelsLike: Int? = null,
    val sunrise: String? = null,
    val sunset: String? = null,
    val pressure: Int? = null,
    val clouds: Int? = null,
    val visibility: Int? = null
) {

    companion object {

        fun fromRetrofitCurrentWeather(
            clientCurrentWeather: ClientCurrentWeather,
            dateFormat: SimpleDateFormat,
            sunDateFormat: SimpleDateFormat,
            date: Date
        ): MainWeatherInfo {

            val cityName = clientCurrentWeather.name
            val cityLat = clientCurrentWeather.coord?.lat
            val cityLon = clientCurrentWeather.coord?.lon
            val temp = clientCurrentWeather.main?.temp?.toInt()
            val feelsLike = clientCurrentWeather.main?.feels_like?.toInt()

            val dt = clientCurrentWeather.dt
            val dtTxt = if (dt != null) {
                dtToTxt(dt, dateFormat, date)
            } else null

            val sunriseInt = clientCurrentWeather.sys?.sunrise
            val sunrise = if (sunriseInt != null) {
                dtToTxt(sunriseInt, sunDateFormat, date)
            } else null

            val sunsetInt = clientCurrentWeather.sys?.sunset
            val sunset = if (sunsetInt != null) {
                dtToTxt(sunsetInt, sunDateFormat, date)
            } else null

            val pressure = clientCurrentWeather.main?.pressure
            val clouds = clientCurrentWeather.clouds?.all
            val visibility = clientCurrentWeather.visibility

            return MainWeatherInfo(
                cityName, cityLat, cityLon,
                dtTxt, dt, temp,
                feelsLike, sunrise, sunset,
                pressure, clouds, visibility
            )
        }

        fun fromRetrofitForecastWeather(
            clientForecastWeather: ClientForecastWeather,
            forecastWeatherList: List<ForecastWeatherItem>,
            dateFormat: SimpleDateFormat,
            sunDateFormat: SimpleDateFormat,
            date: Date
        ): MainWeatherInfo? {

            if (forecastWeatherList.isNotEmpty()) {
                val cityName = clientForecastWeather.city?.name
                val cityLat = clientForecastWeather.city?.coord?.lat
                val cityLon = clientForecastWeather.city?.coord?.lon

                val dt = forecastWeatherList[0].dt
                val dtTxt = if (dt != null) {
                    dtToTxt(dt, dateFormat, date)
                } else null

                val sunriseInt = forecastWeatherList[0].sys?.sunrise
                val sunrise = if (sunriseInt != null) {
                    dtToTxt(sunriseInt, sunDateFormat, date)
                } else null

                val sunsetInt = forecastWeatherList[0].sys?.sunset
                val sunset = if (sunsetInt != null) {
                    dtToTxt(sunsetInt, sunDateFormat, date)
                } else null

                var temp = 0.0
                var feelsLike = 0.0
                var pressure = 0.0
                var clouds = 0.0
                var visibility = 0.0
                val listSize = forecastWeatherList.size
                forecastWeatherList.forEach { forecastWeatherItem ->
                    forecastWeatherItem.main?.temp?.let { tempTemp ->
                        temp += tempTemp
                    }

                    forecastWeatherItem.main?.feels_like?.let { tempFeelsLike ->
                        feelsLike += tempFeelsLike
                    }

                    forecastWeatherItem.main?.pressure?.let { tempPressure ->
                        pressure += tempPressure
                    }

                    forecastWeatherItem.clouds?.all?.let { tempClouds ->
                        clouds += tempClouds
                    }

                    forecastWeatherItem.visibility?.let { tempVisibility ->
                        visibility += tempVisibility
                    }
                }

                temp /= listSize
                feelsLike /= listSize
                pressure /= listSize
                clouds /= listSize
                visibility /= listSize

                return MainWeatherInfo(
                    cityName, cityLat, cityLon,
                    dtTxt, dt, temp.toInt(),
                    feelsLike.toInt(), sunrise, sunset,
                    pressure.toInt(), clouds.toInt(), visibility.toInt()
                )
            } else return null

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
