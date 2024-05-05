package ru.asyncan.geo.plugins

import io.ktor.server.application.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
    }
}
