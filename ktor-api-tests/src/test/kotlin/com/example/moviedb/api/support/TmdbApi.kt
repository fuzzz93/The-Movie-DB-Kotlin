package com.example.moviedb.api.support

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class TmdbApi : AutoCloseable {

    private val client = HttpClient(CIO) {
        expectSuccess = false
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun popularMovies(page: Int = 1, apiKey: String = TmdbConfig.API_KEY): HttpResponse =
        client.get("${TmdbConfig.BASE_URL}/movie/popular") {
            parameter("api_key", apiKey)
            parameter("page", page)
        }

    suspend fun movieDetails(movieId: Long, apiKey: String = TmdbConfig.API_KEY): HttpResponse =
        client.get("${TmdbConfig.BASE_URL}/movie/$movieId") {
            parameter("api_key", apiKey)
        }

    suspend fun searchMovie(query: String, page: Int = 1, apiKey: String = TmdbConfig.API_KEY): HttpResponse =
        client.get("${TmdbConfig.BASE_URL}/search/movie") {
            parameter("api_key", apiKey)
            parameter("query", query)
            parameter("page", page)
        }

    override fun close() = client.close()
}
