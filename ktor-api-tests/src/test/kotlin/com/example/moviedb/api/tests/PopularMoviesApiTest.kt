package com.example.moviedb.api.tests

import com.example.moviedb.api.support.MovieListResponse
import com.example.moviedb.api.support.TmdbError
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@Epic("TMDb API")
@Feature("Popular movies")
class PopularMoviesApiTest : BaseApiTest() {

    @Test
    @Story("Успешный запрос")
    @DisplayName("GET /movie/popular возвращает 200 и непустой список")
    fun popularReturnsResults() = runBlocking {
        val response = api.popularMovies(page = 1)

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<MovieListResponse>()
        assertEquals(1, body.page)
        assertTrue(body.results.isNotEmpty())
    }

    @Test
    @Story("Успешный запрос")
    @DisplayName("GET /movie/popular учитывает параметр page")
    fun popularRespectsPageParameter() = runBlocking {
        val response = api.popularMovies(page = 2)

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<MovieListResponse>()
        assertEquals(2, body.page)
    }

    @Test
    @Story("Ошибка авторизации")
    @DisplayName("GET /movie/popular с неверным api_key возвращает 401")
    fun popularWithInvalidKeyReturns401() = runBlocking {
        val response = api.popularMovies(apiKey = "INVALID_KEY")

        assertEquals(HttpStatusCode.Unauthorized, response.status)
        val error = response.body<TmdbError>()
        assertEquals(7, error.statusCode)
        assertTrue(!error.success)
    }
}
