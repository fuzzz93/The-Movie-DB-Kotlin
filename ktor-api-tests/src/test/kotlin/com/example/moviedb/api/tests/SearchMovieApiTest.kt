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
@Feature("Search movie")
class SearchMovieApiTest : BaseApiTest() {

    @Test
    @Story("Успешный запрос")
    @DisplayName("GET /search/movie по валидному запросу возвращает 200 и результаты")
    fun searchReturnsResults() = runBlocking {
        val response = api.searchMovie(query = "Batman")

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<MovieListResponse>()
        assertTrue(body.results.isNotEmpty())
        assertTrue(body.results.any { it.title?.contains("Batman", ignoreCase = true) == true })
    }

    @Test
    @Story("Успешный запрос")
    @DisplayName("GET /search/movie по бессмысленному запросу возвращает 200 и пустой список")
    fun searchWithNonsenseQueryReturnsEmpty() = runBlocking {
        val response = api.searchMovie(query = "zzxqwmovienotexists12345")

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<MovieListResponse>()
        assertEquals(0, body.totalResults)
        assertTrue(body.results.isEmpty())
    }

    @Test
    @Story("Ошибка: невалидный параметр")
    @DisplayName("GET /search/movie с page=0 возвращает 400")
    fun searchWithInvalidPageReturns400() = runBlocking {
        val response = api.searchMovie(query = "Batman", page = 0)

        assertEquals(HttpStatusCode.BadRequest, response.status)
        val error = response.body<TmdbError>()
        assertEquals(22, error.statusCode)
        assertTrue(!error.success)
    }
}
