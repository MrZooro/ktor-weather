package ru.weather.asyncan

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import ru.weather.asyncan.plugins.*
import io.ktor.serialization.kotlinx.json.*
import ru.weather.asyncan.client.WeatherClient

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val databaseClient = KMongo.createClient("mongodb://localhost:27017").coroutine
    val database = databaseClient.getDatabase("default_db")

    val weatherClient = WeatherClient()

    configureRouting(database, weatherClient)
    configureSerialization()
    configureSwagger()
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}

fun Application.configureSwagger() {
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "swagger"
            forwardRoot = true
        }
        info {
            title = "Sample API"
            version = "1.0"
            description = "Swagger for Sample API"
        }
    }
}
